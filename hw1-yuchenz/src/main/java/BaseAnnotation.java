

/* First created by JCasGen Wed Oct 10 21:33:29 EDT 2012 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Base of all annotation subclasses
 * Updated by JCasGen Tue Oct 16 21:37:48 EDT 2012
 * XML source: /home/yuchenz/Documents/academics/2012fall/11-791 Software Engineering/workspace/hw1-yuchenz/hw1-yuchenz/src/main/java/descriptors/HuskieGeneRecognitionEvaluator.xml
 * @generated */
public class BaseAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(BaseAnnotation.class);
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
  protected BaseAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public BaseAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public BaseAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public BaseAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: source

  /** getter for source - gets Describes where this annotation come from. 
   * @generated */
  public String getSource() {
    if (BaseAnnotation_Type.featOkTst && ((BaseAnnotation_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "BaseAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((BaseAnnotation_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets Describes where this annotation come from.  
   * @generated */
  public void setSource(String v) {
    if (BaseAnnotation_Type.featOkTst && ((BaseAnnotation_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "BaseAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((BaseAnnotation_Type)jcasType).casFeatCode_source, v);}    
  }

    