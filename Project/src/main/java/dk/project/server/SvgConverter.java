// Package
package dk.project.server;

// Imports
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class SvgConverter {

    // Attributes

    // _________________________________________________________

    public static void convertSvgToPng(String svgContent, Path outputPath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputPath.toFile())) {
            PNGTranscoder transcoder = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new java.io.StringReader(svgContent));
            TranscoderOutput output = new TranscoderOutput(os);
            transcoder.transcode(input, output);
        }
    }

} // SvgConverter end