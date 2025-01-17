/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.hop.neo4j.transforms.gencsv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

public class CsvFile {

  private String filename;

  private String shortFilename;

  private String fileType;

  private CompressionFormat compression;

  @SuppressWarnings("java:S2065") // disable sonar warning on transient
  private transient List<IdType> propsList;

  @SuppressWarnings("java:S2065") // disable sonar warning on transient
  private transient Map<String, Integer> propsIndexes;

  @SuppressWarnings("java:S2065") // disable sonar warning on transient
  private transient OutputStream outputStream;

  @SuppressWarnings("java:S2065") // disable sonar warning on transient
  private transient String idFieldName;

  public CsvFile() {
    propsList = new ArrayList<>();
    propsIndexes = new HashMap<>();
  }

  public CsvFile(String filename, String shortFilename, String fileType, CompressionFormat compression) {
    this();
    this.filename = filename;
    this.shortFilename = shortFilename;
    this.fileType = fileType;
    this.compression = compression;
  }

  public void openFile() throws IOException {
    switch (this.compression) {
      case None:
       outputStream = new FileOutputStream(filename);
        break;
      case Zstd:
        FileOutputStream internalStream = new FileOutputStream(filename);
        outputStream = new ZstdCompressorOutputStream(internalStream);
        break;
    }
  }

  public void closeFile() throws IOException {
    if (outputStream != null) {
      outputStream.flush();
      outputStream.close();
    }
  }

  /**
   * Gets outputStream
   *
   * @return value of outputStream
   */
  public OutputStream getOutputStream() {
    return outputStream;
  }

  /** @param outputStream The outputStream to set */
  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CsvFile csvFile = (CsvFile) o;
    return filename.equals(csvFile.filename);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename);
  }

  /**
   * Gets filename
   *
   * @return value of filename
   */
  public String getFilename() {
    return filename;
  }

  /** @param filename The filename to set */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /**
   * Gets fileType
   *
   * @return value of fileType
   */
  public String getFileType() {
    return fileType;
  }

  /** @param fileType The fileType to set */
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  /**
   * Gets shortFilename
   *
   * @return value of shortFilename
   */
  public String getShortFilename() {
    return shortFilename;
  }

  /** @param shortFilename The shortFilename to set */
  public void setShortFilename(String shortFilename) {
    this.shortFilename = shortFilename;
  }

  /**
   * Gets propsList
   *
   * @return value of propsList
   */
  public List<IdType> getPropsList() {
    return propsList;
  }

  /** @param propsList The propsList to set */
  public void setPropsList(List<IdType> propsList) {
    this.propsList = propsList;
  }

  /**
   * Gets propsIndexes
   *
   * @return value of propsIndexes
   */
  public Map<String, Integer> getPropsIndexes() {
    return propsIndexes;
  }

  /** @param propsIndexes The propsIndexes to set */
  public void setPropsIndexes(Map<String, Integer> propsIndexes) {
    this.propsIndexes = propsIndexes;
  }

  /**
   * Gets idFieldName
   *
   * @return value of idFieldName
   */
  public String getIdFieldName() {
    return idFieldName;
  }

  /** @param idFieldName The idFieldName to set */
  public void setIdFieldName(String idFieldName) {
    this.idFieldName = idFieldName;
  }
}
