

/* First created by JCasGen Wed Oct 10 23:59:56 EDT 2012 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Oct 11 00:20:36 EDT 2012
 * XML source: /home/yuchenz/Documents/academics/2012fall/11-791 Software Engineering/workspace/hw1-yuchenz/hw1-yuchenz/src/main/java/descriptors/GeneInputSentenceAnnotator.xml
 * @generated */
public class GeneInputSentence extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneInputSentence.class);
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
  protected GeneInputSentence() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GeneInputSentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GeneInputSentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GeneInputSentence(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (GeneInputSentence_Type.featOkTst && ((GeneInputSentence_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "GeneInputSentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneInputSentence_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (GeneInputSentence_Type.featOkTst && ((GeneInputSentence_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "GeneInputSentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneInputSentence_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (GeneInputSentence_Type.featOkTst && ((GeneInputSentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "GeneInputSentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneInputSentence_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (GeneInputSentence_Type.featOkTst && ((GeneInputSentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "GeneInputSentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneInputSentence_Type)jcasType).casFeatCode_text, v);}    
  }

    