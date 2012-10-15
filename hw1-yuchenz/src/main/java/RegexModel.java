import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.jcas.JCas;

public class RegexModel extends BaseModel {
  protected ArrayList<Pattern> regexList = null;

  public RegexModel(String regexModelPath) throws IOException {
    regexList = new ArrayList<Pattern>();
    loadModelFromFile(regexModelPath);
  }
  
  private RegexModel() {
    
  }

  public void loadModelFromFile(String regexModelPath) throws IOException {
    BufferedReader fileReader = new BufferedReader(new FileReader(regexModelPath));
    String line = null;

    while ((line = fileReader.readLine()) != null) {
      line = line.replaceAll("\n", "");
      Pattern ptn = Pattern.compile(line);
      regexList.add(ptn);
    }

    fileReader.close();
  }

  @Override
  public GeneAnnotation[] annotateLine(String line, JCas aJCas) {
    ArrayList<GeneAnnotation> annotationList = new ArrayList<GeneAnnotation>();

    for (int i = 0; i < regexList.size(); i++) {
      Pattern regex = regexList.get(i);
      Matcher matcher = regex.matcher(line);

      while (matcher.find()) {
        int begin = matcher.start();
        int end = matcher.end();
        String gene = line.substring(begin, end);
        GeneAnnotation geneAnnot = new GeneAnnotation(aJCas, begin, end);
        geneAnnot.setGene(gene);

        System.err.printf("\n\t%s located %s from %d to %d ... ", regex.pattern(), gene, begin, end);
        
        annotationList.add(geneAnnot);
      }
    }

    GeneAnnotation[] annotations = new GeneAnnotation[annotationList.size()];
    annotationList.toArray(annotations);

    return annotations;
  }
  
  public static void main(String[] args) throws IOException {
    String modelPath = "src/main/resources/data/regex_1.model";
    RegexModel rm = new RegexModel(modelPath);
    // rm.annotateLine(line, aJCas)
  }
}
