import java.io.IOException;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

public abstract class BaseModel {
  public abstract void loadModelFromFile(String modelPath) throws IOException;

  public abstract Annotation[] annotateLine(String line, JCas aJCas);
}
