import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;

public class HuskieGeneFileSystemCasConsumer extends CasConsumer_ImplBase {

  public static final String PARAM_OUTPUT_FILE = "outputFile";

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    System.err.println("HuskieGeneFileSystemCasConsumer ... ");
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    String outputFilename = ((String) getConfigParameterValue(PARAM_OUTPUT_FILE)).trim();
    BufferedWriter fileWriter = null;;
    try {
      fileWriter = new BufferedWriter(new FileWriter(outputFilename, true));
    } catch (IOException e) {
      System.err.printf("Unable to write to %s ... \n", outputFilename);
      return;
    }

    FSIndex geneIndex = jcas.getAnnotationIndex(GeneAnnotation.type);
    Iterator geneIndexIterator = geneIndex.iterator();

    while (geneIndexIterator.hasNext()) {
      GeneAnnotation geneAnnot = (GeneAnnotation) geneIndexIterator.next();
      try {
        fileWriter.write(String.format("%s|%d %d|%s\n", geneAnnot.getSentenceId(),
                geneAnnot.getBegin(), geneAnnot.getEnd(), geneAnnot.getGene()));
      } catch (IOException e) {
        System.err.printf("Unable to write to %s ... \n", outputFilename);
        return;
      }
    }
    
    try {
      fileWriter.close();
    } catch (IOException e) {
      System.err.printf("Unable to close file %s ... \n", outputFilename);
    }
  }

}
