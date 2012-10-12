import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;

public class HuskieGeneFileSystemCasConsumer extends CasConsumer_ImplBase {

  public static final String PARAM_OUTPUT_FILE = "OutputFile";
  
  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }
    
    String inputFilename = ((String) getConfigParameterValue(PARAM_OUTPUT_FILE)).trim();
    FSIndex geneIndex = jcas.getAnnotationIndex(GeneAnnotation.type);
    Iterator geneIndexIterator = geneIndex.iterator();
    
    while (geneIndexIterator.hasNext()) {
      GeneAnnotation geneAnnot = (GeneAnnotation) geneIndexIterator.next();
      
    }
  }

}
