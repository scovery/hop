/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.pipeline.transforms.pipelineexecutor;

import org.apache.hop.core.Const;
import org.apache.hop.core.injection.Injection;
import org.apache.hop.core.xml.XmlHandler;
import org.w3c.dom.Node;

/**
 * The workflow to be executed in the pipeline can receive parameters. These are either coming from
 * an input row (the first row in a group of rows) or from a static variable or value.
 */
public class PipelineExecutorParameters implements Cloneable {

  public static final String XML_TAG = "parameters";

  private static final String XML_VARIABLES_TAG = "variable_mapping";

  /** The name of the variable to set in the workflow */
  @Injection(name = "PARAMETERS_VARIABLES", group = "PARAMETERS")
  private String[] variable;

  @Injection(name = "PARAMETERS_FIELDS", group = "PARAMETERS")
  private String[] field;

  /** This is a simple String with optionally variables in them */
  @Injection(name = "PARAMETERS_INPUTS", group = "PARAMETERS")
  private String[] input;

  /** This flag causes the workflow to inherit all variables from the parent pipeline */
  private boolean inheritingAllVariables;

  public PipelineExecutorParameters() {
    super();

    variable = new String[] {};
    field = new String[] {};
    input = new String[] {};

    inheritingAllVariables = true;
  }

  public void allocate(int nrVariables) {
    variable = new String[nrVariables];
    field = new String[nrVariables];
    input = new String[nrVariables];
  }

  @Override
  public Object clone() {
    try {
      PipelineExecutorParameters retval = (PipelineExecutorParameters) super.clone();
      int nrVariables = variable.length;
      retval.allocate(nrVariables);
      System.arraycopy(variable, 0, retval.variable, 0, nrVariables);
      System.arraycopy(field, 0, retval.field, 0, nrVariables);
      System.arraycopy(input, 0, retval.input, 0, nrVariables);
      return retval;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e); // Nope, we don't want that in our code.
    }
  }

  public PipelineExecutorParameters(Node paramNode) {

    int nrVariables = XmlHandler.countNodes(paramNode, XML_VARIABLES_TAG);
    allocate(nrVariables);

    for (int i = 0; i < variable.length; i++) {
      Node variableMappingNode = XmlHandler.getSubNodeByNr(paramNode, XML_VARIABLES_TAG, i);

      variable[i] = XmlHandler.getTagValue(variableMappingNode, "variable");
      field[i] = XmlHandler.getTagValue(variableMappingNode, "field");
      input[i] = XmlHandler.getTagValue(variableMappingNode, "input");
    }

    inheritingAllVariables =
        "Y".equalsIgnoreCase(XmlHandler.getTagValue(paramNode, "inherit_all_vars"));
  }

  public String getXml() {
    StringBuilder xml = new StringBuilder(200);

    xml.append("    ").append(XmlHandler.openTag(XML_TAG));

    for (int i = 0; i < variable.length; i++) {
      xml.append("       ").append(XmlHandler.openTag(XML_VARIABLES_TAG));
      xml.append(XmlHandler.addTagValue("variable", variable[i], false));
      xml.append(XmlHandler.addTagValue("field", field[i], false));
      xml.append(XmlHandler.addTagValue("input", input[i], false));
      xml.append(XmlHandler.closeTag(XML_VARIABLES_TAG)).append(Const.CR);
    }
    xml.append("    ").append(XmlHandler.addTagValue("inherit_all_vars", inheritingAllVariables));
    xml.append("    ").append(XmlHandler.closeTag(XML_TAG));

    return xml.toString();
  }

  /** @return the field name to use */
  public String[] getField() {
    return field;
  }

  /** @param field the input field name to set */
  public void setField(String[] field) {
    this.field = field;
  }

  /** @return the variable */
  public String[] getVariable() {
    return variable;
  }

  /** @param variable the variable to set */
  public void setVariable(String[] variable) {
    this.variable = variable;
  }

  /** @return the inheritingAllVariables */
  public boolean isInheritingAllVariables() {
    return inheritingAllVariables;
  }

  /** @param inheritingAllVariables the inheritingAllVariables to set */
  public void setInheritingAllVariables(boolean inheritingAllVariables) {
    this.inheritingAllVariables = inheritingAllVariables;
  }

  /** @return the input */
  public String[] getInput() {
    return input;
  }

  /** @param input the input to set */
  public void setInput(String[] input) {
    this.input = input;
  }
}
