package org.apache.hop.neo4j.transforms.gencsv;

public enum CompressionFormat {
  None, // Don't calculate unique nore or relationship values
  Zstd, // Compress with Zstd algorithm
// UpdateProperties, // Not supported yet.  Update all the available properties
;

public static final CompressionFormat getCompressionFormat(String name) {
    for (CompressionFormat format : values()) {
      if (format.name().equalsIgnoreCase(name)) {
        return format;
      }
    }
    return None;
  }

  public static final String[] getNames() {
    String[] names = new String[values().length];
    for (int i = 0; i < names.length; i++) {
      names[i] = values()[i].name();
    }
    return names;
  }
}