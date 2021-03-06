package edu.psu.swe.commons.jaxrs.adapters;

/*
 * The Pennsylvania State University © 2016
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class is intended to provide an adaptor to convert Java Integers's into Strings
 *  in JAXB produced documents.
 * 
 * @author crh5255
 *
 */

public class IntegerAsStringAdapter extends XmlAdapter<String, Integer> {

  @Override
  public String marshal(Integer integer) {
    if (integer == null) {
      return null;
    }

    return integer.toString();
  }

  @Override
  public Integer unmarshal(String integer) throws Exception {
    if (integer == null || integer.isEmpty()) {
      return null;
    }

    return Integer.parseInt(integer);
  }
}
