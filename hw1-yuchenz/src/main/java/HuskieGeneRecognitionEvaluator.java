import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceProcessException;

public class HuskieGeneRecognitionEvaluator extends CasConsumer_ImplBase {

  public static final String PARAM_REFERENCE_PATH = "referencePath";
  public static final String PARAM_RESULT_PATH = "resultPath";

  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    System.err.println("HuskieGeneRecognitionEvaluator ... ");

    // String referenceFilename = ((String) getConfigParameterValue(PARAM_REFERENCE_PATH)).trim();
    // String resultFilename = ((String) getConfigParameterValue(PARAM_RESULT_PATH)).trim();
    String referenceFilename = "src/main/resources/data/sample.out";
    String resultFilename = "hw1-yuchenz.out";
    BufferedReader fileReader = null;

    try {
      fileReader = new BufferedReader(new FileReader(referenceFilename));
    } catch (FileNotFoundException e) {
      System.err.printf("Unable to open reference file %s ... \n", referenceFilename);
      return;
    }

    String line = "";
    HashMap<String, Integer> referenceList = new HashMap<String, Integer>();
    try {
      while ((line = fileReader.readLine()) != null) {
        line = line.replaceAll("\n", "");
        referenceList.put(line, 0);
      }
    } catch (IOException e) {
      System.err.printf("Unable to read from reference file %s ... \n", referenceFilename);
      try {
        fileReader.close();
      } catch (IOException e1) { }
      return;
    }
    try {
      fileReader.close();
    } catch (IOException e) {
      return;
    }
    
    int relevantCount = referenceList.size();
    int retrievedCount = 0;
    int retrievedRelaventCount = 0;
    
    BufferedReader resultReader = null;
    try {
      resultReader = new BufferedReader(new FileReader(resultFilename));
    } catch (FileNotFoundException e) {
      System.err.printf("Unable to read result file %s ... \n", resultFilename);
      return;
    }
    
    try {
      while ((line = resultReader.readLine()) != null) {
        line = line.replaceAll("\n", "");
        if (referenceList.containsKey(line)) {
          retrievedRelaventCount++;
        }
        retrievedCount++;
      }
    } catch (IOException e) {
      System.err.printf("Unable to read from result file %s ... \n", referenceFilename);
      try {
        resultReader.close();
      } catch (IOException e1) { }
      return;
    }
    try {
      resultReader.close();
    } catch (IOException e) { }
//
//    FSIndex geneIndex = jcas.getAnnotationIndex(GeneAnnotation.type);
//    Iterator geneIndexIterator = geneIndex.iterator();
    

    
    
    
    
//
//    while (geneIndexIterator.hasNext()) {
//      GeneAnnotation geneAnnot = (GeneAnnotation) geneIndexIterator.next();
//      String geneAnnotStr = geneAnnot.getSentenceId() + "|" + geneAnnot.getStart() + "| "
//              + geneAnnot.getEnd() + "|" + geneAnnot.getGene();
//      if (referenceList.containsKey(geneAnnotStr)) {
//        retrievedRelaventCount++;
//      }
//    }
    
    double precision = ((double) retrievedRelaventCount) / ((double) retrievedCount);
    double recall = ((double) retrievedRelaventCount) / ((double) relevantCount);
    double fscore = 2 * precision * recall / (precision + recall);
    System.out.printf("Precision=%.6f\n", precision);
    System.out.printf("Recall=%.6f\n", recall);
    System.out.printf("F1 score=%.6f\n", fscore);
  }

  public static void main(String args[]) throws ResourceProcessException {
    HuskieGeneRecognitionEvaluator eval = new HuskieGeneRecognitionEvaluator();
    ((HuskieGeneRecognitionEvaluator) eval).processCas((CAS) null);
  }
  
}
