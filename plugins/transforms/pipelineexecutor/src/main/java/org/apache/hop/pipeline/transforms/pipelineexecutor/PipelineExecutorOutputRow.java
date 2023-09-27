package org.apache.hop.pipeline.transforms.pipelineexecutor;

import org.apache.hop.core.injection.Injection;
import org.apache.hop.core.row.value.ValueMetaFactory;

public class PipelineExecutorOutputRow {
  @Injection(name = "OUTPUT_ROWS_NAME", group = "RESULT_ROWS")
  private String name;

  private int type;

  @Injection(name = "OUTPUT_ROWS_LENGTH", group = "RESULT_ROWS")
  private int length;

  @Injection(name = "OUTPUT_ROWS_PRECISION", group = "RESULT_ROWS")
  private int precision;

  public PipelineExecutorOutputRow() {
    super();

    name = "";
    type = 0;
    length = -1;
    precision = -1;
  }

  public PipelineExecutorOutputRow(String name, int type, int length, int precision ) {
    super();

    this.name = name;
    this.type = type;
    this.length = length;
    this.precision = precision;
  }

  public PipelineExecutorOutputRow clone() {
    return new PipelineExecutorOutputRow(name, type, length, precision);
  }

  public Integer length() {
    return length;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Injection(name = "OUTPUT_ROWS_TYPES", group = "RESULT_ROWS")
  public void setType(String type) {
    this.type = ValueMetaFactory.getIdForValueMeta(type);
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getPrecision() {
    return precision;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public void setAll(String name, int type, int length, int precision) {
    this.name = name;
    this.type = type;
    this.length = length;
    this.precision = precision;
  }
}