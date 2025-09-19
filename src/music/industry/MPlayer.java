package music.industry;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MPlayer extends JFrame implements ActionListener{
    private Container pane = getContentPane();
    private JButton play = new JButton("  Play  ");
    private JButton pause = new JButton("Pause");
    private JButton reset = new JButton("Reset");
    private JButton previous = new JButton("Previous");
    private JButton next = new JButton("    Next    ");
    private JButton exit = new JButton("  Exit  ");
    private JSlider volume = new JSlider();
    private Clip clip = AudioSystem.getClip();
    private File file;
    private AudioInputStream audioInputStream;
    private String songname;
    private Timer timer;
    //    private File file = new File("Aaj dil shayarana.wav");
//    private AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//    private File file;
    private String[] songs = {"src\\music\\industry\\Aaj dil shayarana.wav","src\\music\\industry\\Jab Bhi.wav","src\\music\\industry\\koi kese tumhen.wav"};
    private int clicknext = 0;
    private int clickprevious = 2;
    //    private AudioInputStream audioInputStream;
    private String response="";
    private GridBagConstraints gridBagConstraints;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private JPanel panelpicture = new JPanel();
    private JLabel songtitle = new JLabel();
    private JLabel picturelabel = new JLabel();
    private Icon icon = new ImageIcon("src\\music\\industry\\music.gif");
    private JPanel progresspanel = new JPanel();
    private JProgressBar songbar = new JProgressBar();
    private JPanel panelbuttons = new JPanel();
    private int songmaxposition ;
    private int songminiposition =0;
    MPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        file = new File(songs[clicknext]);
        audioInputStream = AudioSystem.getAudioInputStream(file);

        pane.setLayout(null);
        pane.add(panelpicture);
        pane.add(progresspanel);
        pane.add(panelbuttons);
        pane.setBackground(new Color(2, 91, 83, 255));
        panelpicture.setBackground(new Color(2, 91, 83, 255));
        progresspanel.setBackground(new Color(2, 86, 78, 255));
        panelbuttons.setBackground(new Color(2, 91, 83, 255));

        panelpicture.setBounds(5,5,400,330);
        progresspanel.setBounds(5,335,400,60);
        panelbuttons.setBounds(5,395,400,170);

        panelpicture.setBorder(BorderFactory.createTitledBorder("Picture & Song Panel"));
        panelpicture.setLayout(new FlowLayout());
        panelpicture.add(songtitle);
        panelpicture.add(picturelabel);

        picturelabel.setBorder(BorderFactory.createLineBorder(new Color(120, 153, 224), 2,true));
        picturelabel.setIcon(icon);
        picturelabel.setPreferredSize(new Dimension(350,250));

        songtitle.setBackground(new Color(2, 91, 83));
        songtitle.setForeground(new Color(255,255,255));
        songname = file.getName();
        songtitle.setText(songname);
        songtitle.setOpaque(true);
        songtitle.setFont(new Font("SansSerif", Font.PLAIN,20));

        progresspanel.setBorder(BorderFactory.createTitledBorder("Song Progress View"));
        progresspanel.setLayout(new FlowLayout());
        progresspanel.add(songbar);
        songbar.setPreferredSize(new Dimension(380,25));
        songbar.setStringPainted(true);
        songbar.setValue(45);
        songmaxposition= 0;
//        songbar.setString(String.valueOf((double) clip.getMicrosecondPosition()/6000000));
        songbar.setMaximum(songmaxposition);
        songbar.setMinimum(songminiposition);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (songbar.getValue()>=0)
                    songbar.setBackground(new Color(241, 241, 64));
//                System.out.println(String.valueOf((double) clip.getMicrosecondPosition()/6000000));
//                play.setText("");
                songbar.setValue((int) clip.getMicrosecondPosition());
//                    songbar.setString(String.valueOf((int)clip.getMicrosecondLength()/6000000));


            }
        });

        panelbuttons.setBorder(BorderFactory.createTitledBorder("Buttons Panel"));
        panelbuttons.setLayout(gridBagLayout);
        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.insets=new Insets(0,0,5,0);
        panelbuttons.add(play,gridBagConstraints);
        play.addActionListener(this);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        gridBagConstraints.insets=new Insets(0,5,10,5);
        panelbuttons.add(pause,gridBagConstraints);
        pause.addActionListener(this);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=0;
        gridBagConstraints.insets=new Insets(0,5,5,5);
        panelbuttons.add(reset,gridBagConstraints);
        reset.addActionListener(this);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        gridBagConstraints.insets=new Insets(0,5,10,5);
        panelbuttons.add(previous,gridBagConstraints);
        previous.addActionListener(this);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        gridBagConstraints.insets=new Insets(0,0,5,0);
        panelbuttons.add(next,gridBagConstraints);
        next.addActionListener(this);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=1;
        gridBagConstraints.insets=new Insets(0,0,10,0);
        panelbuttons.add(exit,gridBagConstraints);
        exit.addActionListener(this);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=3;
        gridBagConstraints.gridheight=1;
        gridBagConstraints.insets=new Insets(0,0,0,0);
        panelbuttons.add(volume,gridBagConstraints);
        volume.setValue(50);
        volume.setMajorTickSpacing(5);
        volume.setMinimum(0);
        volume.setMaximum(100);
        volume.setPaintTicks(true);
        volume.setPaintLabels(false);
        volume.setOrientation(JSlider.HORIZONTAL);
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volume.setBackground(new Color(volume.getValue(), 233, volume.getValue()));
//                SoundbankReader sbr = new SoundbankReader() {
//                    @Override
//                    public Soundbank getSoundbank(URL url) throws InvalidMidiDataException, IOException {
//                        return null;
//                    }
//
//                    @Override
//                    public Soundbank getSoundbank(InputStream stream) throws InvalidMidiDataException, IOException {
//                        return null;
//                    }
//
//                    @Override
//                    public Soundbank getSoundbank(File file) throws InvalidMidiDataException, IOException {
//                        return null;
//                    }
//                };
//                try {
//                    sbr.getSoundbank(new File("Nayan.mp3"));
//                } catch (InvalidMidiDataException | IOException ex) {
//                    ex.printStackTrace();
//                }
            }
        });
        clip.open(audioInputStream);

        setTitle("Music Player");
        setIconImage(new ImageIcon("src\\music\\industry\\music.gif").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700,50,430,610);
        setResizable(true);
        setVisible(true);
    }
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("Playing...........");
        MPlayer mp = new MPlayer();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==play){
//            System.out.println(clip.getMicrosecondLength()/1000000);
//            System.out.println("Seconds: "+clip.getMicrosecondPosition()/1000);
            clip.start();
            if (timer.isRunning()){
                timer.stop();
                play.setText("Start");
            }else if (!timer.isRunning()){
                timer.start();
                play.setText("stop");
            }
        }
        else if (e.getSource() == pause){
            int a=1,b=0,c = 0;
//            System.out.println(clip.getMicrosecondLength()/60000000);
//            System.out.println(clip.getMicrosecondPosition());
            a = (int) (clip.getMicrosecondPosition()/1000000);
            b = a/60;
            boolean d = (a>=60);
            a=1;
//            System.out.println("minutes: "+b+":"+c);
//            if (a <= 60) {
//                System.out.println("Seconds: "+a);
//                System.out.printf("%d:%d",a,b);
//            }
            clip.stop();
        }
        else if (e.getSource() == reset){
//            System.out.println(clip.getMicrosecondLength()/60000000.0);
//            System.out.println("Playing......");
            clip.setMicrosecondPosition(0);
        }
        else if (e.getSource() == next){
            if (clip.isOpen()){
                clip.close();
                file = new File(songs[clicknext]);
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(file);
                } catch (UnsupportedAudioFileException | IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    clip.open(audioInputStream);
                } catch (LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
                songtitle.setText(file.getName());
                clip.setMicrosecondPosition(0);
                clip.start();
                clicknext++;
                if (clicknext>2){
                    clicknext=0;
                }
            }
        }
        else if (e.getSource() == previous){
            if (clip.isOpen()){
                clip.close();
                file = new File(songs[clickprevious]);
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(file);
                } catch (UnsupportedAudioFileException | IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    clip.open(audioInputStream);
                } catch (LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
                songtitle.setText(file.getName());
                clip.setMicrosecondPosition(0);
                clip.start();
                clickprevious--;
                if (clickprevious<0){
                    clickprevious=2;
                }
            }
        }
        else if (e.getSource() == exit){
            System.exit(0);
        }

    }
}
