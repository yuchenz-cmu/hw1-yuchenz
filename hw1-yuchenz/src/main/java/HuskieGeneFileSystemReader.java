import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Progress;

public class HuskieGeneFileSystemReader extends CollectionReader_ImplBase {

  protected boolean processed;

  public static final String PARAM_INPUT_FILE = "InputFile";

  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    String inputFilename = ((String) getConfigParameterValue(PARAM_INPUT_FILE)).trim();

    File inputFile = new File(inputFilename);
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    
    String text = FileUtils.file2String(inputFile);
      // put document in CAS
    jcas.setDocumentText(text);
    
    processed = true;
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    if (processed) {
      processed = false;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

}
