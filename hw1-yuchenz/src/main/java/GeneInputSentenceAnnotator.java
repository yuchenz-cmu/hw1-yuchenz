import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;

public class GeneInputSentenceAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // System.err.println("GeneInputSentenceAnnotator ... ");
    getContext().getLogger().log(Level.ALL, "GeneInputSentenceAnnotator ... ");
    String docText = aJCas.getDocumentText();

    String[] sents = docText.split("\n");
    int currStart = 0;
    for (String sent : sents) {
      int idx = sent.indexOf(' ');
      if (idx == -1) {
        throw new AnalysisEngineProcessException(String.format(
                "Unable to process %s in document text!", sent), null);
      }
      GeneInputSentence sentAnnot = new GeneInputSentence(aJCas, currStart, currStart + sent.length());
      sentAnnot.setId(sent.substring(0, idx));
      sentAnnot.setText(sent.substring(idx + 1, sent.length()));
      sentAnnot.addToIndexes();
      currStart += sent.length() + 1;
    }
  }

}
