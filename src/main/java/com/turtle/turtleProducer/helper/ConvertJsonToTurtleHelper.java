package com.turtle.turtleProducer.helper;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFLib;

public class ConvertJsonToTurtleHelper {

  private final InputStream jsonIn;
  private final OutputStream rdfOut;
  private Charset charset = StandardCharsets.UTF_8;

  private String baseUri;

  public ConvertJsonToTurtleHelper(InputStream csvIn, OutputStream rdfOut, String baseUri) {
    this.jsonIn = csvIn;
    this.rdfOut = rdfOut;
    this.baseUri = baseUri;
  }

  public String convert() {
    JsonParserFactory factory = Json.createParserFactory(null);
    JsonParser parser = factory.createParser(jsonIn, StandardCharsets.UTF_8);
    StreamRDF streamRDF =
        StreamRDFLib.writer(new BufferedWriter(new OutputStreamWriter(rdfOut, charset)));
    streamRDF.start();
    write(parser, streamRDF, IRIResolver.create(baseUri));
    streamRDF.finish();
    return rdfOut.toString();
  }

  public static void write(JsonParser parser, StreamRDF rdfStream, IRIResolver iriResolver) {
    Deque<Node> subjectStack = new ArrayDeque<>();
    Map<Node, Node> arrayProperties = new HashMap<>();

    Node property = null;
    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();

      switch (event) {
        case START_ARRAY:
          if (!subjectStack.isEmpty() && property != null)
            arrayProperties.put(subjectStack.getLast(), property);
          break;
        case END_ARRAY:
          if (!subjectStack.isEmpty()) arrayProperties.remove(subjectStack.getLast());
          break;
        case START_OBJECT:
          Node subject = NodeFactory.createBlankNode();
          if (property != null && !subjectStack.isEmpty())
            rdfStream.triple(new Triple(subjectStack.getLast(), property, subject));
          subjectStack.addLast(subject);
          break;
        case END_OBJECT:
          subjectStack.removeLast();
          if (!subjectStack.isEmpty() && arrayProperties.containsKey(subjectStack.getLast()))
            property = arrayProperties.get(subjectStack.getLast());
          break;
        case VALUE_FALSE:
          rdfStream.triple(
              new Triple(
                  subjectStack.getLast(),
                  property,
                  NodeFactory.createLiteralByValue(Boolean.FALSE, XSDDatatype.XSDboolean)));
          break;
        case VALUE_TRUE:
          rdfStream.triple(
              new Triple(
                  subjectStack.getLast(),
                  property,
                  NodeFactory.createLiteralByValue(Boolean.TRUE, XSDDatatype.XSDboolean)));
          break;
        case KEY_NAME:
          property = NodeFactory.createURI(iriResolver.resolveToString("#" + parser.getString()));
          break;
        case VALUE_STRING:
          if (property != null)
            rdfStream.triple(
                new Triple(
                    subjectStack.getLast(),
                    property,
                    NodeFactory.createLiteral(parser.getString())));
          break;
        case VALUE_NUMBER:
          try {
            rdfStream.triple(
                new Triple(
                    subjectStack.getLast(),
                    property,
                    NodeFactory.createLiteralByValue(
                        Integer.valueOf(parser.getString()), XSDDatatype.XSDint)));
          } catch (NumberFormatException ex) {
            rdfStream.triple(
                new Triple(
                    subjectStack.getLast(),
                    property,
                    NodeFactory.createLiteralByValue(
                        Float.valueOf(parser.getString()), XSDDatatype.XSDfloat)));
          }
          break;
        case VALUE_NULL:
          break;
      }
    }
  }
}
