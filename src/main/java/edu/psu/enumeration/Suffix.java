/**
 * 
 */
package edu.psu.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ses44
 *
 */
public enum Suffix
{
  NONE(""),
  JR("Jr."),
  SR("Sr."),
  II("II"),
  III("III"),
  IV("IV"),
  V("V"),
  VI("VI"),
  VII("VII"),
  VIII("VIII"),
  IX("IX"),
  X("X");
  
  private String prettyString_;
  private static Map<String, Suffix> reverseTranslation_ = new HashMap<String, Suffix>();
  private static String ILLEGAL_ARGUMENT_MESSAGE = null;
  
  static
  {
    EnumSet<Suffix> set = EnumSet.allOf(Suffix.class);
    
    StringBuilder sb = new StringBuilder();

    Iterator<Suffix> iter = set.iterator();
    
    while(iter.hasNext())
    {
      Suffix s = iter.next();
      sb.append(s.name());
      
      if (iter.hasNext())
      {
        sb.append(", ");
      }
           
      reverseTranslation_.put(s.prettyString_,  s);
    }
     
    ILLEGAL_ARGUMENT_MESSAGE = "The value you passed for SUFFIX was illegal, legal values are: " + sb.toString();
  }
  
  Suffix(String prettyString)
  {
    prettyString_ = prettyString;
  }
  
  /**
   * Translates from the English readable string to the appropriate Enumerted value
   * @param prettyString
   * @return Prefix or null if either not found or a null argument is received
   */
  public static Suffix fromPrettyString(String prettyString)
  {
    if (prettyString != null)
    { 
      if (prettyString.trim().isEmpty())
      {
        return Suffix.NONE;
      }
      
      return reverseTranslation_.get(prettyString.trim());
    }
    
    return null;
  }
  
  @Override
  /**
   * Returns an English readable representation of the enumeration or an empty string Prefix == None
   * @return String
   */
  public String toString()
  {
    if (this.equals(NONE))
    {
      return " ";
    }
    
    return prettyString_;
  }
  
  public static Suffix enumValue(String stringValue)
  {
    if (stringValue == null)
    {
      throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
    }
    
    Suffix s = null;
    try
    {
      s = Suffix.valueOf(stringValue.toUpperCase());
    }
    catch(IllegalArgumentException e)
    {
      //Try from pretty string
      s = Suffix.fromPrettyString(stringValue);
      if (s == null)
      {
        throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
      }
    }
    
    return s;
  }
}
