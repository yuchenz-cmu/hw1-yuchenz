

/* First created by JCasGen Wed Oct 10 21:34:16 EDT 2012 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Oct 11 00:20:36 EDT 2012
 * XML source: /home/yuchenz/Documents/academics/2012fall/11-791 Software Engineering/workspace/hw1-yuchenz/hw1-yuchenz/src/main/java/descriptors/GeneInputSentenceAnnotator.xml
 * @generated */
public class GeneAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GeneAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GeneAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GeneAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GeneAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: sentenceId

  /** getter for sentenceId - gets 
   * @generated */
  public String getSentenceId() {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "GeneAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_sentenceId);}
    
  /** setter for sentenceId - sets  
   * @generated */
  public void setSentenceId(String v) {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_sentenceId == null)
      jcasType.jcas.throwFeatMissing("sentenceId", "GeneAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_sentenceId, v);}    
   
    
  //*--------------*
  //* Feature: gene

  /** getter for gene - gets 
   * @generated */
  public String getGene() {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_gene == null)
      jcasType.jcas.throwFeatMissing("gene", "GeneAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_gene);}
    
  /** setter for gene - sets  
   * @generated */
  public void setGene(String v) {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_gene == null)
      jcasType.jcas.throwFeatMissing("gene", "GeneAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_gene, v);}    
  }

    