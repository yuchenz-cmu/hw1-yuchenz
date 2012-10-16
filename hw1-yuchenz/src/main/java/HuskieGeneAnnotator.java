import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public class HuskieGeneAnnotator extends JCasAnnotator_ImplBase {

  public static final String PARAM_REGEX_MODEL_PATH = "regexModelPath";

  protected RegexModel regexModel = null;

  protected void loadModels() throws IOException {
    String regexModelPath = ((String) getContext().getConfigParameterValue(PARAM_REGEX_MODEL_PATH))
            .trim();
    regexModel = new RegexModel(regexModelPath);
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.err.println("HuskieGeneAnnotator ... ");
    // load the model
    if (regexModel == null) {
      try {
        loadModels();
      } catch (IOException e) {
        System.err.printf("Unable to load model ...\n");
        return;
      }
    }

    FSIndex sentIndex = aJCas.getAnnotationIndex(GeneInputSentence.type);

    Iterator sentIndexIterator = sentIndex.iterator();
    while (sentIndexIterator.hasNext()) {
      GeneInputSentence sent = (GeneInputSentence) sentIndexIterator.next();
      System.err.printf("Annotating %s ... ", sent.getId());
      
      // GeneAnnotation geneAnnot = new GeneAnnotation(aJCas, sent.getBegin(), sent.getBegin() + 1);
      // geneAnnot.setGene("Dummy");
      // geneAnnot.setSentenceId(sent.getId());
      // geneAnnot.addToIndexes();

      GeneAnnotation[] regexGeneAnnotations = regexModel.annotateLine(sent.getText(), aJCas);
      System.err.printf("got %d annotations ... \n", regexGeneAnnotations.length);
      
      for (GeneAnnotation annot : regexGeneAnnotations) {
        annot.setSentenceId(sent.getId());
        annot.addToIndexes();
      }
    }
  }

}
