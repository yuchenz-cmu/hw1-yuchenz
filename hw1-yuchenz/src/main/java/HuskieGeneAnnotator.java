import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;


public class HuskieGeneAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FSIndex sentIndex = aJCas.getAnnotationIndex(GeneInputSentence.type);
    
    Iterator sentIndexIterator = sentIndex.iterator();
    while (sentIndexIterator.hasNext()) {
      GeneInputSentence sent = (GeneInputSentence) sentIndexIterator.next();
      
      GeneAnnotation geneAnnot = new GeneAnnotation(aJCas, sent.getBegin(), sent.getBegin() + 1);
      geneAnnot.setGene("Dummy");
      geneAnnot.setSentenceId(sent.getId());
      geneAnnot.addToIndexes();
    }
  }

}
