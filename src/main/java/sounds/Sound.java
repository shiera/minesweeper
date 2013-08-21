package sounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound implements Runnable {



    private String soundName;


    /**
     *
     * @param soundName name of the sound. should should be in the sounds package
     */
    public Sound(String soundName){

        this.soundName = soundName;
    }

    public void play(){
        Thread tread = new Thread(this);
        tread.start();
    }


    private void playSound() {
        try {

            File soundFile =   new File("src//sounds//" + soundName + ".wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(soundFile);
            AudioFormat format = fileFormat.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = null;
            if (AudioSystem.isLineSupported(info)){
                try {
                    line = (SourceDataLine) AudioSystem.getLine(info);
                    line.open(format);
                } catch (final Exception ex) {
                    System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
                }
            }
            if (line != null){
                line.start();
                int bytesRead = 0;
                byte[] data = new byte[524288]; //128kb puskuria
                try {
                    while (bytesRead!=-1){
                        bytesRead = stream.read(data, 0, data.length);
                        if (bytesRead>=0){
                            line.write(data, 0, bytesRead);
                        }
                    }
                    line.drain();
                    line.close();
                } catch (final Exception ex){
                    System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
                }
            }
        } catch (final  IOException ex) {
            System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
        } catch (UnsupportedAudioFileException  ex){
            System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
        }
    }


    @Override
    public void run() {
        playSound();
    }
}
