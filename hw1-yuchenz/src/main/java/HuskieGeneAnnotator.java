import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

/**
 * Main Gene name annotator, uses regular expression model. 
 * 
 * @author yuchenz
 *
 */
public class HuskieGeneAnnotator extends JCasAnnotator_ImplBase {

  public static final String PARAM_REGEX_MODEL_PATH = "regexModelPath";

  protected RegexModel regexModel = null;

  /**
   * Loads the model from file given by configuration parameter "regexModelPath"
   * @throws IOException can't find or can't open the model file. 
   */
  protected void loadModels() throws IOException {
    String regexModelPath = ((String) getContext().getConfigParameterValue(PARAM_REGEX_MODEL_PATH))
            .trim();
    regexModel = new RegexModel(regexModelPath);
  }


  /**
   * Annotate all the sentences, add to index. 
   */
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
      
      GeneAnnotation[] regexGeneAnnotations = regexModel.annotateLine(sent.getText(), aJCas);
      System.err.printf("got %d annotations ... \n", regexGeneAnnotations.length);
      
      for (GeneAnnotation annot : regexGeneAnnotations) {
        annot.setSentenceId(sent.getId());
        annot.addToIndexes();
      }
    }
  }

}
