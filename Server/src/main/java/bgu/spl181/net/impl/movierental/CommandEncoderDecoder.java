package bgu.spl181.net.impl.movierental;

import bgu.spl181.net.api.MessageEncoderDecoder;
import bgu.spl181.net.api.ustbp.Command;
import com.sun.deploy.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class CommandEncoderDecoder implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    @Override
    public Command decodeNextByte(byte nextByte) {
        if (nextByte == '\n') {
            return popCommand();
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Command message) {
        return new byte[0];
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private Command popCommand() {
        String stringAux= "";
        try {
            stringAux  =  new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> listAux = Arrays.asList(StringUtils.splitString(stringAux, " "));
        return Command.generate(listAux);
    }
}
