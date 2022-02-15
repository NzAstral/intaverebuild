package de.jpx3.intave.user.storage;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import static java.util.zip.Deflater.BEST_COMPRESSION;

@SuppressWarnings("UnstableApiUsage")
public interface Storage {
  void writeTo(ByteArrayDataOutput output);
  void readFrom(ByteArrayDataInput input);

  default void read(ByteBuffer buffer) {
    byte[] array = buffer.array();
    if (array.length == 0) {
      return;
    }
    byte[] bytes = decompress(array);
    readFrom(ByteStreams.newDataInput(bytes));
  }

  default ByteBuffer write() {
    ByteArrayDataOutput output = ByteStreams.newDataOutput();
    writeTo(output);
    byte[] bytes = output.toByteArray();
    return ByteBuffer.wrap(compress(bytes));
  }

  static byte[] compress(byte[] in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      DeflaterOutputStream deflater = new DeflaterOutputStream(out, new Deflater(BEST_COMPRESSION));
      deflater.write(in);
      deflater.flush();
      deflater.close();
      return out.toByteArray();
    } catch (Exception exception) {
      exception.printStackTrace();
      return new byte[0];
    }
  }

  static byte[] decompress(byte[] in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      InflaterOutputStream inflater = new InflaterOutputStream(out, new Inflater());
      inflater.write(in);
      inflater.flush();
      inflater.close();
      return out.toByteArray();
    } catch (Exception exception) {
      exception.printStackTrace();
      return new byte[0];
    }
  }
}
