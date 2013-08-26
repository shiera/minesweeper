package sounds;

import java.io.File;
import java.io.IOException;
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

    /**
     * makes a new tread to the sound and plays it
     */
    public void play(){
        Thread tread = new Thread(this);
        tread.start();
    }


    /**
     * loads the sound to a line and plays it
     */
    private void loadSound() {
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
                    ex.printStackTrace();
                }
            }
            if (line != null){
                line.start();
                int bytesRead = 0;
                byte[] data = new byte[524288]; //puskuri
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
                    ex.printStackTrace();
                }
            }
        } catch (final  IOException ex) {
            System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException  ex){
            System.out.println("Exception in sound: "+ex.getClass()+" exception: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * don't use this, use play() instead
     */
    @Override
    public void run() {
        loadSound();
    }
}
