/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sudoku;

import java.awt.AlphaComposite;
import sudoku.generator.Generate_Sudoku;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.sound.sampled.*;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 *
 * @author susmit
 */
class RoundButton extends javax.swing.JButton {
  private Color color; private boolean paintarmed=true;
  public RoundButton(String label,Color color) {
    super(label);

    setBackground(java.awt.Color.white); 
     setFocusable(false); this.color=color; //Color.GRAY;
     setForeground(color); //setBorderPainted(true);
     Font newButtonFont=new Font("Liberation Sans",1,15);
     setFont(newButtonFont);
    /*
     These statements enlarge the button so that it 
     becomes a circle rather than an oval.
    */
    /*java.awt.Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);*/
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
  public RoundButton(String label,Color color,boolean paintarmed) {
    this(label,color);

    this.paintarmed=paintarmed;
  
  }
  public RoundButton(javax.swing.Icon icon){
      super(icon);
      
    java.awt.Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
 
    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
    setContentAreaFilled(false);
  }
  protected void paintComponent(java.awt.Graphics g1) {
    java.awt.Graphics2D g=(java.awt.Graphics2D)g1;  
    if (getModel().isArmed()&&paintarmed) {
      g.setColor(new Color(242,242,248));
    } else {
      g.setColor(getBackground());
    }
    //g.setStroke(new java.awt.BasicStroke((float)1.5));
    //g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
    g.fillRoundRect(0, 0, getSize().width -1, getSize().height - 1,12,12);
    //g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
 
    super.paintComponent(g);
  }
 
  protected void paintBorder(java.awt.Graphics g) {
    java.awt.Graphics2D g2=(java.awt.Graphics2D)g;
    g2.setColor(paintarmed ? color : Color.WHITE);  // steel blue
    g2.setStroke(new java.awt.BasicStroke((float)2));
    g2.drawRoundRect(0, 0, getSize().width -1, getSize().height - 1,12,12);
    //g2.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
    //g.drawOval(0, 0, getSize().width -1, getSize().height -1);
  }
  //@Override
  /*public void paint(java.awt.Graphics g) {
      super.paint(g);
      //paintBorder(g); paintComponent(g);
  }
  public void update(java.awt.Graphics g){
      super.update(g);
  }*/
  public void setBackground(Color col){
      super.setBackground(col);
  }
  public void setForeground(Color col){
      super.setForeground(col);
  }
  public void setFont(Font font){
      super.setFont(font);
  }
  public Font getFont(Font font){
      return super.getFont();
  }
  @Override
  public void repaint(){
      if(this.getBackground()!=null&&color!=null&&this.getBackground().getRGB()==color.getRGB()){
        this.paintarmed=false; //System.out.println("yes");
      }
      //else this.paintarmed=true;
      super.repaint(); //this.paintarmed=true;
  }
  // Hit detection.
  //java.awt.Shape shape;
 
  /*public boolean contains(int x, int y) {
    // If the button has changed size,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }*/
}
class BlurLayerUI extends javax.swing.plaf.LayerUI<Component> {
    //private final int mFadeCount = 10,mFadeLimit = 15;
    private BufferedImage mOffscreenImage;
  private BufferedImageOp mOperation;

  public BlurLayerUI() {
    float ninth = 1.0f / 144.0f;
    float[] blurKernel = new float[144];
    Arrays.fill(blurKernel, ninth);
    mOperation = new ConvolveOp(
            new Kernel(12, 12, blurKernel),
            ConvolveOp.EDGE_NO_OP, null);
  }
    @Override
  public void paint (Graphics g, JComponent c) {
    int w = c.getWidth();
    int h = c.getHeight();
 
    // Paint the view.
    //super.paint (g, c);
 
    /*if (!mIsRunning) {
      return;
    }*/
    
    if (w == 0 || h == 0) {
      return;
    }

    // Only create the off-screen image if the one we have
    // is the wrong size.
    if (mOffscreenImage == null ||
            mOffscreenImage.getWidth() != w ||
            mOffscreenImage.getHeight() != h) {
      mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }

    Graphics2D ig2 = mOffscreenImage.createGraphics();
    ig2.setClip(g.getClip());
    super.paint(ig2, c);
    ig2.dispose();

    Graphics2D g2 = (Graphics2D)g;
    g2.drawImage(mOffscreenImage, mOperation, 0, 0);
    /*Graphics2D g2 = (Graphics2D)g.create();
 
    float fade = (float)mFadeCount / (float)mFadeLimit;
    // Gray it out.
    Composite urComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(
        AlphaComposite.SRC_OVER, .5f * fade));
    g2.fillRect(0, 0, w, h);
    g2.setComposite(urComposite);
    g2.dispose();*/
  }
  /*public void start(){
      firePropertyChange("tick", 0, 1);
  }
  @Override
  public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
    if ("tick".equals(pce.getPropertyName())) {
      l.repaint();
    }
  }*/
}
public class Sudoku extends javax.swing.JFrame {

    /**
     * Creates new form Sudoku
     */
    private class Pair{
        int i,j;
        public Pair(int i,int j){
            this.i=i; this.j=j;
        }
    }
    private final javax.swing.JButton grid[][]=new RoundButton[9][9];
    private Generate_Sudoku generator;
    private final int rgb;
    private final HashMap<javax.swing.JButton,Pair> Ingrid=new HashMap<>();
   //private final HashSet<?>[][] Inrow= new HashSet<?>[9][10];
    //private final HashSet<?>[][] Incol= new HashSet<?>[9][10];
    private final int[][] Inrow=new int[9][10];
    private final int[][] Incol=new int[9][10];
    private final int[][] Inbox=new int[9][10];
    private boolean inbox=false,newgame=false; 
    private javax.swing.JButton active; private int K,I=0;
    private final javax.swing.Timer Timer; 
    private javax.swing.Timer timer;
    private int seconds=0;
    private final String soundName = "/home/susmit/NetBeansProjects/sudoku/src/sudoku/peace1.wav";  
    private javax.swing.border.Border border;
    private java.awt.Container contentpane;
    
    private class DragListener extends MouseInputAdapter
    {
        Point location;
        MouseEvent pressed;
        Component component=null;

        public DragListener(java.awt.Component component){
            this.component=component;
        }
        public DragListener() {}
        public void mousePressed(MouseEvent me)
        {
            pressed = me;
        }

        public void mouseDragged(MouseEvent me)
        {
            //Component component = me.getComponent().getParent();
            Component component=(this.component==null ? me.getComponent() : this.component);

            location = component.getLocation(location);
            int x = location.x - pressed.getX() + me.getX();
            int y = location.y - pressed.getY() + me.getY();
            component.setLocation(x, y);
        }
    }
    public Sudoku() {
       
        BlurLayerUI layerUI = new BlurLayerUI();
        initComponents(); //this.setEnabled(false);
        contentpane=this.getContentPane();
        
        javax.swing.JLayer<Component> jlayer = new javax.swing.JLayer<Component>(contentpane, layerUI);
        
        this.setContentPane(jlayer);
        
        //java.awt.Dimension DimMax = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //this.setMaximumSize(DimMax);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        //layerUI.start();
        DragListener drag=new DragListener(jDialog3),drag_=new DragListener();
        
        java.net.URL helpURL = Sudoku.class.getResource("TextSampleDemoHelp.html");
        jEditorPane1.setEditable(false);
        if (helpURL != null) {
            try {
                jEditorPane1.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: TextSampleDemoHelp.html");
        }
        
        jEditorPane1.addMouseListener(drag);
        jEditorPane1.addMouseMotionListener(drag);
        
        jDialog3.addMouseListener(drag_);
        jDialog3.addMouseMotionListener(drag_);
        
        rgb=jPanel2.getBackground().getRGB();
        /*for(int i=0;i<9;i++){
            for(int j=0;j<9;j++) grid[i][j].setFont(new java.awt.Font("Liberation Sans", 1, 24));
        }*/
        /*javax.swing.ImageIcon icon=new javax.swing.ImageIcon ( "/home/susmit/Desktop/button/world.png" );
        java.awt.Image image=icon.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH);
        try{
            StyledDocument doc2 = (StyledDocument) jTextPane1.getDocument();
      Style style2 = doc2.addStyle("StyleName", null);
      StyleConstants.setIcon(style2,new javax.swing.ImageIcon(image));
        doc2.insertString(doc2.getLength(), "\nHello World", style2);
        }
        catch(BadLocationException e){}*/
        
        //jTextPane1.insertIcon(new javax.swing.ImageIcon(image));
        
        //jTextPane1.setText("<html><img src=\"world.png\" alt=\"Image not found\" width=\"100\" height=\"100\"></html>");
        //jPanel2.setBackground(new Color(237,239,245));
        //jPanel2.setBackground(new Color(250, 249, 246));
        //jPanel2.setBackground(new Color(242, 242, 242));
        //jPanel2.setBackground(new Color(rgb));
        //jButton2.setText("    ");
        /*int d=1;
        for(int i=0;i<9;i++){ 
            for(int j=0;j<9;j++){
                grid[i][j].setText(Integer.toString(d)); d++; }
        }*/
        //jPanel2.setBackground(new java.awt.Color(239, 237, 245));
        //char c='\u23F2';
        //System.out.println("\u265B");
        javax.swing.ImageIcon clock=new javax.swing.ImageIcon("/home/susmit/NetBeansProjects/sudoku/src/sudoku/timer2_.png");
        java.awt.Image img=clock.getImage().getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH);
        clock=new javax.swing.ImageIcon(img);
        jLabel9.setIcon(clock);
        jLabel7.setText("  ");
        for(int i=0;i<9;i++){
            for(int j=1;j<10;j++){
                Inrow[i][j]=0; //new HashSet<Integer>();
                Incol[i][j]=0;  //new HashSet<Integer>();
                Inbox[i][j]=0;
            }
        }
        mapgrid(); K=44+(int)(Math.random()*11);
        generator=new Generate_Sudoku(9,K);
        generator.fillValues();
        /*int[][] mat={{0,0,0,0,0,0,0,0,7},{0,4,3,0,6,7,0,9,0},{0,5,0,0,2,0,1,0,0},{0,1,7,2,0,3,0,8,0},{0,0,0,0,9,0,4,0,0},{2,0,0,4,0,0,0,5,1},{0,0,4,0,1,0,5,0,0},{0,7,1,0,8,2,6,0,0},{5,0,0,6,3,0,0,1,9}};
        generator.mat=mat;*/
        generator.printSudoku(generator.solved);
        addGridActionListeners();
        addInboxListeners();
        
        javax.swing.UIManager.put("OptionPane.background", Color.white);
        javax.swing.UIManager.put("OptionPane.messagebackground", Color.white);
        javax.swing.UIManager.put("Panel.background", Color.white);
        javax.swing.UIManager.put("Button.background", Color.white);    
        
        int interval=1000;
        java.awt.event.ActionListener taskperformer=new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++; int hrs=seconds/3600,mins=(seconds/60)%60,secs=seconds%60;
                String time="";
                if(hrs>0){
                    if(hrs<10) time+=0;
                    time+=hrs+":";
                }
                if(mins<10){
                    time+=0; 
                }
                time+=mins+":";
                if(secs<10){
                    time+=0;
                }
                time+=secs;
                jLabel9.setText(time);
            }
        };
        Timer=new javax.swing.Timer(interval, taskperformer);
        //Timer.start(); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton93 = new RoundButton("RButton1",new Color(96,130,182));
        jButton94 = new RoundButton("RButton1",new Color(96,130,182));
        jButton95 = new RoundButton("RButton1",new Color(96,130,182));
        jButton96 = new RoundButton("RButton1",new Color(96,130,182));
        jButton97 = new RoundButton("RButton1",new Color(96,130,182));
        jButton98 = new RoundButton("RButton1",new Color(96,130,182));
        jButton99 = new RoundButton("RButton1",new Color(96,130,182));
        jButton100 = new RoundButton("RButton1",new Color(96,130,182));
        jButton101 = new RoundButton("RButton1",new Color(96,130,182));
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog(this,true);
        jPanel5 = new javax.swing.JPanel();
        jButton84 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton86 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jDialog3 = new javax.swing.JDialog(this,false);
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton58 = new RoundButton("RButton1",new Color(96,130,182));
        jButton18 = new RoundButton("RButton1",new Color(96,130,182));
        jButton53 = new RoundButton("RButton1",new Color(96,130,182));
        jButton59 = new RoundButton("RButton1",new Color(96,130,182));
        jButton19 = new RoundButton("RButton1",new Color(96,130,182));
        jButton54 = new RoundButton("RButton1",new Color(96,130,182));
        jButton60 = new RoundButton("RButton1",new Color(96,130,182));
        jButton61 = new RoundButton("RButton1",new Color(96,130,182));
        jButton26 = new RoundButton("RButton1",new Color(96,130,182));
        jButton62 = new RoundButton("RButton1",new Color(96,130,182));
        jButton27 = new RoundButton("RButton1",new Color(96,130,182));
        jButton63 = new RoundButton("RButton1",new Color(96,130,182));
        jButton28 = new RoundButton("RButton1",new Color(96,130,182));
        jButton78 = new RoundButton("RButton1",new Color(96,130,182));
        jButton29 = new RoundButton("RButton1",new Color(96,130,182));
        jButton79 = new RoundButton("RButton1",new Color(96,130,182));
        jButton30 = new RoundButton("RButton1",new Color(96,130,182));
        jButton80 = new RoundButton("RButton1",new Color(96,130,182));
        jButton31 = new RoundButton("RButton1",new Color(96,130,182));
        jButton81 = new RoundButton("RButton1",new Color(96,130,182));
        jButton64 = new RoundButton("RButton1",new Color(96,130,182));
        jButton65 = new RoundButton("RButton1",new Color(96,130,182));
        jButton66 = new RoundButton("RButton1",new Color(96,130,182));
        jButton2 = new RoundButton("RButton1",new Color(96,130,182));
        jButton67 = new RoundButton("RButton1",new Color(96,130,182));
        jButton68 = new RoundButton("RButton1",new Color(96,130,182));
        jButton69 = new RoundButton("RButton1",new Color(96,130,182));
        jButton5 = new RoundButton("RButton1",new Color(96,130,182));
        jButton32 = new RoundButton("RButton1",new Color(96,130,182));
        jButton20 = new RoundButton("RButton1",new Color(96,130,182));
        jButton82 = new RoundButton("RButton1",new Color(96,130,182));
        jButton70 = new RoundButton("RButton1",new Color(96,130,182));
        jButton33 = new RoundButton("RButton1",new Color(96,130,182));
        jButton34 = new RoundButton("RButton1",new Color(96,130,182));
        jButton35 = new RoundButton("RButton1",new Color(96,130,182));
        jButton36 = new RoundButton("RButton1",new Color(96,130,182));
        jButton37 = new RoundButton("RButton1",new Color(96,130,182));
        jButton38 = new RoundButton("RButton1",new Color(96,130,182));
        jButton39 = new RoundButton("RButton1",new Color(96,130,182));
        jButton40 = new RoundButton("RButton1",new Color(96,130,182));
        jButton6 = new RoundButton("RButton1",new Color(96,130,182));
        jButton21 = new RoundButton("RButton1",new Color(96,130,182));
        jButton71 = new RoundButton("RButton1",new Color(96,130,182));
        jButton7 = new RoundButton("RButton1",new Color(96,130,182));
        jButton22 = new RoundButton("RButton1",new Color(96,130,182));
        jButton55 = new RoundButton("RButton1",new Color(96,130,182));
        jButton72 = new RoundButton("RButton1",new Color(96,130,182));
        jButton8 = new RoundButton("RButton1",new Color(96,130,182));
        jButton41 = new RoundButton("RButton1",new Color(96,130,182));
        jButton23 = new RoundButton("RButton1",new Color(96,130,182));
        jButton42 = new RoundButton("RButton1",new Color(96,130,182));
        jButton73 = new RoundButton("RButton1",new Color(96,130,182));
        jButton43 = new RoundButton("RButton1",new Color(96,130,182));
        jButton44 = new RoundButton("RButton1",new Color(96,130,182));
        jButton11 = new RoundButton("RButton1",new Color(96,130,182));
        jButton45 = new RoundButton("RButton1",new Color(96,130,182));
        jButton12 = new RoundButton("RButton1",new Color(96,130,182));
        jButton46 = new RoundButton("RButton1",new Color(96,130,182));
        jButton13 = new RoundButton("RButton1",new Color(96,130,182));
        jButton56 = new RoundButton("RButton1",new Color(96,130,182));
        jButton57 = new RoundButton("RButton1",new Color(96,130,182));
        jButton47 = new RoundButton("RButton1",new Color(96,130,182));
        jButton48 = new RoundButton("RButton1",new Color(96,130,182));
        jButton14 = new RoundButton("RButton1",new Color(96,130,182));
        jButton49 = new RoundButton("RButton1",new Color(96,130,182));
        jButton15 = new RoundButton("RButton1",new Color(96,130,182));
        jButton50 = new RoundButton("RButton1",new Color(96,130,182));
        jButton16 = new RoundButton("RButton1",new Color(96,130,182));
        jButton51 = new RoundButton("RButton1",new Color(96,130,182));
        jButton9 = new RoundButton("RButton1",new Color(96,130,182));
        jButton17 = new RoundButton("RButton1",new Color(96,130,182));
        jButton24 = new RoundButton("RButton1",new Color(96,130,182));
        jButton52 = new RoundButton("RButton1",new Color(96,130,182));
        jButton74 = new RoundButton("RButton1",new Color(96,130,182));
        jButton3 = new RoundButton("RButton1",new Color(96,130,182));
        jButton75 = new RoundButton("RButton1",new Color(96,130,182));
        jButton4 = new RoundButton("RButton1",new Color(96,130,182));
        jButton76 = new RoundButton("RButton1",new Color(96,130,182));
        jButton10 = new RoundButton("RButton1",new Color(96,130,182));
        jButton77 = new RoundButton("RButton1",new Color(96,130,182));
        jButton25 = new RoundButton("RButton1",new Color(96,130,182));
        jButton1 = new javax.swing.JButton();
        jButton83 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setBackground(new java.awt.Color(255, 255, 255));
        jDialog1.setUndecorated(true);
        jDialog1.setResizable(false);
        jDialog1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDialog1FocusLost(evt);
            }
        });
        jDialog1.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                jDialog1WindowLostFocus(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 0, 15), new java.awt.Color(96, 130, 182))); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton93.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton93.setText("8");

        jButton94.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton94.setText("4");

        jButton95.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton95.setText("5");

        jButton96.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton96.setText("7");

        jButton97.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton97.setText("6");
        //jButton9.setBackground(new Color(96,130,182));
        //jButton9.setForeground(Color.black);
        grid[1][2]=jButton9;

        jButton98.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton98.setText("9");

        jButton99.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton99.setText("1");
        jButton99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton99ActionPerformed(evt);
            }
        });

        jButton100.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton100.setText("2");

        jButton101.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton101.setText("3");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton96, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton93, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton98, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton94, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton99, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton100, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton95, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton97, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton101, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton96, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton93, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton98, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton94, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton95, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton97, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton99, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton101, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton100, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(96, 130, 182));
        jLabel1.setText("^");
        jLabel1.setBackground(Color.WHITE);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(96, 130, 182));
        jLabel2.setText("<");
        jLabel1.setBackground(Color.WHITE);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(96, 130, 182));
        jLabel3.setText(">");
        jLabel1.setBackground(Color.WHITE);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel1)))
                .addGap(0, 4, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog2.setResizable(false);
        jDialog2.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                jDialog2WindowClosed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton84.setFont(new java.awt.Font("Liberation Sans", 0, 13)); // NOI18N
        jButton84.setText("New Game");
        jButton84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton84ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jLabel4.setText("<html>Congratulations you win ! \u231B</html>");

        jButton86.setFont(new java.awt.Font("Liberation Sans", 0, 13)); // NOI18N
        jButton86.setText("Quit");
        jButton86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton86ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Liberation Sans", 0, 15)); // NOI18N
        jLabel8.setText(" Time taken:   ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton84)
                        .addGap(60, 60, 60)
                        .addComponent(jButton86, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton84)
                    .addComponent(jButton86))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Nimbus Sans", 0, 14)); // NOI18N
        jLabel5.setText("Are you sure you want to clear the board ?");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Nimbus Sans", 0, 14)); // NOI18N
        jLabel6.setText("Are you sure you want to resign ?");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jDialog3.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog3.setBackground(new java.awt.Color(254, 253, 235));
        jDialog3.setUndecorated(true);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jEditorPane1.setEditable(false);
        jEditorPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jEditorPane1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jEditorPane1FocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jEditorPane1);
        jEditorPane1.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
                if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
                    if (java.awt.Desktop.isDesktopSupported()) {
                        try {
                            java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (java.net.URISyntaxException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );

        border = jScrollPane1.getBorder();

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("<html><span style=\"text-decoration: underline;color: blue;\">Skip</span></html>");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel10)
                .addGap(4, 4, 4))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );

        jPanel10.setVisible(false);
        jPanel10.setBackground(new java.awt.Color(249, 249, 246));

        jLabel11.setFont(new java.awt.Font("Nimbus Sans", 0, 15)); // NOI18N
        jLabel11.setText("<html>Let's have a crack ...&ensp;\u231B</html>");

        jLabel12.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel12.setText("<html><span style=\"text-decoration: underline;color: blue;\">Yes</span></html>");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel13.setText("<html><span style=\"text-decoration: underline;\">No</span></html>");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel13))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(41, 41, 41)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(jPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel9, javax.swing.JLayeredPane.DRAG_LAYER);
        jLayeredPane1.setLayer(jPanel10, javax.swing.JLayeredPane.DRAG_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(319, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(90, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(263, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane1)
                .addGap(0, 0, 0))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane1)
                .addGap(0, 0, 0))
        );

        jDialog3.getContentPane().setBackground(new Color(254,254,254));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sudoku");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(470, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(231, 231, 243));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton58.setText(" ");
        grid[7][3]=jButton58;

        jButton18.setText(" ");
        grid[2][4]=jButton18;

        jButton53.setText(" ");
        grid[5][1]=jButton53;

        jButton59.setText(" ");
        grid[8][7]=jButton59;

        jButton19.setText(" ");
        grid[2][5]=jButton19;

        jButton54.setText(" ");
        grid[3][4]=jButton54;

        jButton60.setText(" ");
        grid[7][4]=jButton60;
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton61.setText(" ");
        grid[8][8]=jButton61;
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        jButton26.setText(" ");
        grid[2][6]=jButton26;

        jButton62.setText(" ");
        grid[7][5]=jButton62;

        jButton27.setText(" ");
        grid[2][7]=jButton27;

        jButton63.setText(" ");
        grid[8][3]=jButton63;

        jButton28.setText(" ");
        grid[2][8]=jButton28;

        jButton78.setText(" ");
        grid[8][0]=jButton78;

        jButton29.setText(" ");
        grid[4][8]=jButton29;

        jButton79.setText(" ");
        grid[6][5]=jButton79;

        jButton30.setText(" ");
        grid[5][6]=jButton30;

        jButton80.setText(" ");
        grid[8][1]=jButton80;

        jButton31.setText(" ");
        grid[4][3]=jButton31;

        jButton81.setText(" ");
        grid[6][4]=jButton81;

        jButton64.setText(" ");
        grid[8][4]=jButton64;

        jButton65.setText(" ");
        grid[8][5]=jButton65;

        jButton66.setText(" ");
        grid[6][0]=jButton66;

        jButton2.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton2.setText(" ");
        /*jButton2.setBackground(new Color(96,130,182));
        jButton2.setForeground(Color.white);*/
        grid[0][0]=jButton2;
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton67.setText(" ");
        grid[6][2]=jButton67;

        jButton68.setText(" ");
        grid[6][6]=jButton68;

        jButton69.setText(" ");
        grid[6][1]=jButton69;

        jButton5.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton5.setText(" ");
        /*jButton5.setBackground(new Color(96,130,182));
        jButton5.setForeground(Color.white);*/
        grid[0][2]=jButton5;

        jButton32.setText(" ");
        grid[5][7]=jButton32;

        jButton20.setText(" ");
        grid[0][6]=jButton20;

        jButton82.setText(" ");
        grid[8][2]=jButton82;

        jButton70.setText(" ");
        grid[6][8]=jButton70;

        jButton33.setText(" ");
        grid[4][4]=jButton33;

        jButton34.setText(" ");
        grid[5][8]=jButton34;

        jButton35.setText(" ");
        grid[4][5]=jButton35;

        jButton36.setText(" ");
        grid[5][3]=jButton36;

        jButton37.setText(" ");
        grid[5][4]=jButton37;

        jButton38.setText(" ");
        grid[5][5]=jButton38;

        jButton39.setText(" ");
        grid[3][0]=jButton39;

        jButton40.setText(" ");
        grid[3][2]=jButton40;

        jButton6.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton6.setText(" ");
        /*jButton6.setBackground(new Color(96,130,182));
        jButton6.setForeground(Color.white);*/
        grid[0][1]=jButton6;

        jButton21.setText(" ");
        grid[0][8]=jButton21;

        jButton71.setText(" ");
        grid[7][0]=jButton71;

        jButton7.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton7.setText(" ");
        /*jButton7.setBackground(new Color(96,130,182));
        jButton7.setForeground(Color.white);*/
        grid[1][0]=jButton7;

        jButton22.setText(" ");
        grid[0][7]=jButton22;

        jButton55.setText(" ");
        grid[5][2]=jButton55;

        jButton72.setText(" ");
        grid[6][7]=jButton72;

        jButton8.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton8.setText(" ");
        /*jButton8.setBackground(new Color(96,130,182));
        jButton8.setForeground(Color.white);*/
        grid[1][1]=jButton8;

        jButton41.setText(" ");
        grid[3][6]=jButton41;

        jButton23.setText(" ");
        grid[1][6]=jButton23;

        jButton42.setText(" ");
        grid[3][1]=jButton42;

        jButton73.setText(" ");
        grid[7][1]=jButton73;

        jButton43.setText(" ");
        grid[3][8]=jButton43;

        jButton44.setText(" ");
        grid[4][0]=jButton44;

        jButton11.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton11.setText(" ");
        /*jButton11.setBackground(new Color(96,130,182));
        jButton11.setForeground(Color.white);*/
        grid[0][3]=jButton11;

        jButton45.setText(" ");
        grid[3][7]=jButton45;

        jButton12.setText(" ");
        grid[0][5]=jButton12;

        jButton46.setText(" ");
        grid[4][1]=jButton46;

        jButton13.setText(" ");
        grid[0][4]=jButton13;

        jButton56.setText(" ");
        grid[7][8]=jButton56;

        jButton57.setText(" ");
        grid[8][6]=jButton57;

        jButton47.setText(" ");
        grid[4][6]=jButton47;

        jButton48.setText(" ");
        grid[4][2]=jButton48;

        jButton14.setText(" ");
        grid[1][3]=jButton14;

        jButton49.setText(" ");
        grid[3][3]=jButton49;

        jButton15.setText(" ");
        grid[1][4]=jButton15;

        jButton50.setText(" ");
        grid[4][7]=jButton50;

        jButton16.setText(" ");
        grid[1][5]=jButton16;

        jButton51.setText(" ");
        grid[5][0]=jButton51;

        jButton9.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton9.setText(" ");
        //jButton9.setBackground(new Color(96,130,182));
        //jButton9.setForeground(Color.black);
        grid[1][2]=jButton9;

        jButton17.setText(" ");
        grid[2][3]=jButton17;

        jButton24.setText(" ");
        grid[1][7]=jButton24;

        jButton52.setText(" ");
        grid[3][5]=jButton52;

        jButton74.setText(" ");
        grid[7][6]=jButton74;

        jButton3.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton3.setText(" ");
        /*jButton3.setBackground(new Color(96,130,182));
        jButton3.setForeground(Color.white);*/
        grid[2][0]=jButton3;

        jButton75.setText(" ");
        grid[7][2]=jButton75;

        jButton4.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton4.setText(" ");
        /*jButton4.setBackground(new Color(96,130,182));
        jButton4.setForeground(Color.white);*/
        grid[2][1]=jButton4;

        jButton76.setText(" ");
        grid[6][3]=jButton76;

        jButton10.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        jButton10.setText(" ");
        /*jButton10.setBackground(new Color(96,130,182));
        jButton10.setForeground(Color.white);*/
        grid[2][2]=jButton10;

        jButton77.setText(" ");
        grid[7][7]=jButton77;

        jButton25.setText(" ");
        grid[1][8]=jButton25;

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton66, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton69, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton67, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton80, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton82, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton76, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton81, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton79, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton63, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton68, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton77, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton68, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton77, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton76, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton81, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton79, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton63, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton66, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton69, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton67, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton71, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton82, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton80, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26))
        );

        jButton1.setFont(new java.awt.Font("URW Palladio L", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(96, 130, 182));
        jButton1.setText("Clear Board");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 130, 182), 1, true));
        jButton1.setBackground(Color.WHITE);
        jButton1.setFocusable(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton83.setFont(new java.awt.Font("URW Palladio L", 1, 14)); // NOI18N
        jButton83.setForeground(new java.awt.Color(51, 51, 51));
        jButton83.setText("New Puzzle");
        jButton83.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 1, true));
        jButton83.setBackground(Color.WHITE);
        jButton83.setFocusable(false);
        jButton83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton83MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton83MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton83MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton83MouseReleased(evt);
            }
        });
        jButton83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton83ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jLabel7.setText("00:00");

        jLabel9.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        jLabel9.setText("00:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton83, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton83, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(83, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mapgrid(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Ingrid.put(grid[i][j],new Pair(i,j));
            }
        }
    }
    private void addGridActionListeners(){
    
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int cell=generator.mat[i][j];
                if(cell!=0){
                    Inrow[i][cell]++; Incol[j][cell]++; Inbox[i/3*3+j/3][cell]++;
                    grid[i][j].setBackground(new Color(96,130,182));
                    grid[i][j].setForeground(Color.white);
                    grid[i][j].setText(Integer.toString(cell));
                    /*java.awt.event.ActionListener[] al=grid[i][j].getActionListeners();
                    for(int l=0;l<al.length;l++)
                        grid[i][j].removeActionListener(al[l]);*/
                    //grid[i][j].repaint(); 
                    continue;
                }
                //grid[i][j].setBackground(Color.white);
                if(i<7){
                    grid[i][j].addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           buttonType1ActionPerformed(e); 
                        }
                    });
                }
                else if(j>4){
                    grid[i][j].addActionListener((ActionEvent e) -> {
                        buttonType2ActionPerformed(e);
                    });
                }
                else if(j<4) {
                    grid[i][j].addActionListener(new java.awt.event.ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buttonType3ActionPerformed(e);
                        }
                    });
                }
                else {
                    grid[i][j].addActionListener(new java.awt.event.ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buttonType4ActionPerformed(e);
                        }
                    });
                }
            }
        }
    }
    private void addInboxListeners(){
       java.awt.Component[] components=jPanel4.getComponents();
        for (Component component : components) {
            javax.swing.JButton button = (javax.swing.JButton) component;
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    javax.swing.JButton option=(javax.swing.JButton)e.getSource();
                    String d=active.getText(),n=option.getText();
                    if(!d.equals(n)){
                        Pair place=Ingrid.get(active); int i=place.i,j=place.j,b=i/3*3+j/3;
                        if(!d.equals(" ")) {
                            if(active.getForeground()!=Color.red) K++;
                            int old=Integer.parseInt(d); active.setText(" ");
                            Inrow[i][old]--; Incol[j][old]--; Inbox[b][old]--;
                            if(Inrow[i][old]==1){
                                for(int k=0;k<9;k++){
                                    String s=grid[i][k].getText();
                                    if(!s.equals(" ")&&Integer.parseInt(s)==old){
                                        if(Incol[k][old]==1&&Inbox[i/3*3+k/3][old]==1){
                                            if(grid[i][k].getBackground()!=Color.white)
                                                grid[i][k].setBackground(new Color(92,130,182));
                                            else if(grid[i][k].getForeground()!=Color.black){
                                                K--;
                                                grid[i][k].setForeground(Color.black);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            if(Incol[j][old]==1){
                                for(int k=0;k<9;k++){
                                    String s=grid[k][j].getText();
                                    if(!s.equals(" ")&&Integer.parseInt(s)==old){
                                        if(Inrow[k][old]==1&&Inbox[k/3*3+j/3][old]==1){
                                            if(grid[k][j].getBackground()!=Color.white)
                                                grid[k][j].setBackground(new Color(92,130,182));
                                            else if(grid[k][j].getForeground()!=Color.black){
                                                K--;
                                                grid[k][j].setForeground(Color.black);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            if(Inbox[b][old]==1){
                                int p=b/3,q=b%3;
                                loop: for(int r=3*p;r<3*p+3;r++){
                                        for(int k=0;k<3;k++){
                                            String s=grid[r][3*q+k].getText();
                                            if(!s.equals(" ")&&Integer.parseInt(s)==old){
                                                if(Inrow[r][old]==1&&Incol[3*q+k][old]==1){
                                                    if(grid[r][3*q+k].getBackground()!=Color.white)
                                                        grid[r][3*q+k].setBackground(new Color(92,130,182));
                                                    else if(grid[r][3*q+k].getForeground()!=Color.black){
                                                        K--;
                                                        grid[r][3*q+k].setForeground(Color.black);
                                                    }
                                                }
                                                break loop;
                                            }
                                        }
                                      }
                            }
                        }
                        int cell=Integer.parseInt(n);
                        if(Inrow[i][cell]==1){
                            for(int k=0;k<9;k++){
                                String s=grid[i][k].getText();
                                if(!s.equals(" ")&&Integer.parseInt(s)==cell){
                                    if(grid[i][k].getBackground()!=Color.white)
                                        grid[i][k].setBackground(Color.red);
                                    else if(grid[i][k].getForeground()!=Color.red){
                                        K++;
                                        grid[i][k].setForeground(Color.red);
                                    }
                                    break;
                                }
                            }
                        }
                        if(Incol[j][cell]==1){
                            for(int k=0;k<9;k++){
                                String s=grid[k][j].getText();
                                if(!s.equals(" ")&&Integer.parseInt(s)==cell){
                                    if(grid[k][j].getBackground()!=Color.white)
                                        grid[k][j].setBackground(Color.red);
                                    else if(grid[k][j].getForeground()!=Color.red){
                                        K++;
                                        grid[k][j].setForeground(Color.red);
                                    }
                                    break;
                                }
                            }
                        }
                        if(Inbox[b][cell]==1){
                            int p=b/3,q=b%3;
                            loop: for(int r=3*p;r<3*p+3;r++){
                                    for(int k=0;k<3;k++){
                                        String s=grid[r][3*q+k].getText();
                                        if(!s.equals(" ")&&Integer.parseInt(s)==cell){
                                            if(grid[r][3*q+k].getBackground()!=Color.white)
                                                grid[r][3*q+k].setBackground(Color.red);
                                            else if(grid[r][3*q+k].getForeground()!=Color.red){
                                                K++;
                                                grid[r][3*q+k].setForeground(Color.red);
                                            }
                                            break loop;
                                        }
                                    }
                                  }
                        }    
                        active.setText(n); //active.setFont(new Font("Liberation Sans",1,15));
                        Inrow[i][cell]++; Incol[j][cell]++; Inbox[b][cell]++;
                        if(Inrow[i][cell]>1 || Incol[j][cell]>1 || Inbox[b][cell]>1)
                            active.setForeground(Color.RED);
                        else{     active.setForeground(Color.BLACK); K--; }
                    }
                    inbox=false; active.setBackground(Color.white);
                    jDialog1.dispose();
                    //if(K==0) System.out.println("done");
                    if(K==0){
                        Timer.stop();
                        try{
                            switch_mode();
                        }
                        catch(IOException ex){}
                    }
                }
            });
        }
    }
    private void switch_mode() throws IOException{
        this.setEnabled(false);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(grid[i][j].getBackground()==Color.white) 
                    grid[i][j].setForeground(new Color(96,130,182));
            }
        }
        try{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new java.io.File(soundName).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(volume.getValue()-2f);
        clip.start();
        }
        catch(UnsupportedAudioFileException e){ System.out.println("Unsupported audio!"); }
        catch(LineUnavailableException e){}
        
        int delay = 300; I=0; //milliseconds
        java.awt.event.ActionListener taskPerformer;
        taskPerformer = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //for(int i=0;i<9;i++){
                update();
                
                //}
            }
        };
        timer=new javax.swing.Timer(delay, taskPerformer);
        //timer.setRepeats(false); 
        timer.setInitialDelay(300);
        timer.start(); 
    }
    private void update(){
        if(I==9){ timer.stop(); //jPanel2.setBackground(new Color(250, 249, 246));
        int hrs=seconds/3600,mins=(seconds/60)%60,secs=seconds%60;
        String time=jLabel8.getText();
        if(hrs!=0) time+=hrs+" "+(hrs>1 ? "hours " : "hour ");
        if(mins!=0) time+=mins+" "+(mins>1 ? "minutes " : "minute ");
        if(secs!=0) time+=secs+" "+(secs>1 ? "seconds " : "second ");
        jLabel8.setText(time); //+"59 hours 59 minutes 59 seconds");
        jDialog2.setSize(420, 300);
        jDialog2.setLocationRelativeTo(this);
        
        jDialog2.setVisible(true); return; }
        for(int j=0;j<9;j++){
                    if(grid[I][j].getBackground()==Color.white){
                        grid[I][j].setBackground(new Color(96,130,182));
                        grid[I][j].setForeground(Color.white);
                        grid[I][j].repaint();
                    }
                    if(grid[j][I].getBackground()==Color.white){
                        grid[j][I].setBackground(new Color(96,130,182));
                        grid[j][I].setForeground(Color.white);
                        grid[j][I].repaint();
                    }
         }
        I++;
    }
    private void buttonType1ActionPerformed(java.awt.event.ActionEvent e) {
        active=(javax.swing.JButton)e.getSource();
        active.setBackground(new Color(242,242,248));
        java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        jDialog1.setBounds((int)pt.getX()-100, (int)pt.getY(), 212, 243);
        //jDialog1.setBounds(this.getX()+340, this.getY()+(int)getMousePosition().getY(), 215, 240);
        jLabel2.setText("  "); jLabel3.setText(" "); jLabel1.setText("^"); //(int)(getMousePosition().getX()/1.3)
        jDialog1.setVisible(true); inbox=true;
    }
    private void buttonType2ActionPerformed(java.awt.event.ActionEvent e) {
        active=(javax.swing.JButton)e.getSource();
        active.setBackground(new Color(242,242,248));
        java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        jDialog1.setBounds((int)pt.getX(), (int)pt.getY()-115, 212, 243);
        //jDialog1.setBounds(this.getX()+840, this.getY()+(int)getMousePosition().getY()-115, 214, 240);
        jLabel1.setText(" "); jLabel3.setText(" "); jLabel2.setText("<");
        jDialog1.setVisible(true); inbox=true;
    }
    private void buttonType3ActionPerformed(java.awt.event.ActionEvent e) {
        active=(javax.swing.JButton)e.getSource();
        active.setBackground(new Color(242,242,248));
        java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        jDialog1.setBounds((int)pt.getX()-212, (int)pt.getY()-115, 212, 243);
        jLabel1.setText(" "); jLabel2.setText("  "); jLabel3.setText(">");
        jDialog1.setVisible(true); inbox=true;
    }
    private void buttonType4ActionPerformed(java.awt.event.ActionEvent e) {
        active=(javax.swing.JButton)e.getSource();
        active.setBackground(new Color(242,242,248));
        java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        java.awt.Point pt1=new java.awt.Point(jButton60.getLocation());
        SwingUtilities.convertPointToScreen(pt1, jButton60);
        //System.out.println(pt.getX()+" "+pt1.getX());
        if(pt1.getX()-pt.getX()>=218){
            jDialog1.setBounds((int)pt.getX()-215, (int)pt.getY()-115, 212, 243);
            jLabel1.setText(" "); jLabel2.setText("  "); jLabel3.setText(">");
        }
        else{
            jDialog1.setBounds((int)pt.getX(), (int)pt.getY()-115, 212, 243);
            jLabel1.setText(" "); jLabel3.setText(" "); jLabel2.setText("<");
        }
        //System.out.println(pt.getX()+" "+this.getX()+" "+(int)getMousePosition().getX());
        //jDialog1.setBounds(this.getX()+840, this.getY()+(int)getMousePosition().getY()-115, 214, 240);
        jDialog1.setVisible(true); inbox=true;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //System.out.println(this.getX());
        /*java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        jDialog1.setBounds((int)pt.getX()-100, (int)pt.getY(), 214, 240);
        //jDialog1.setBounds(this.getX()+340, this.getY()+(int)getMousePosition().getY(), 215, 240);
        jLabel2.setText("  "); jLabel3.setText(" "); jLabel1.setText("^"); //(int)(getMousePosition().getX()/1.3)
        jDialog1.setVisible(true); inbox=true;*/
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        // TODO add your handling code here:
        /*java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        jDialog1.setBounds((int)pt.getX(), (int)pt.getY()-115, 214, 240);
        //jDialog1.setBounds(this.getX()+840, this.getY()+(int)getMousePosition().getY()-115, 214, 240);
        jLabel1.setText(" "); jLabel3.setText(" "); jLabel2.setText("<");
        jDialog1.setVisible(true); inbox=true;*/
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        jButton1.setBackground(new Color(242,242,242));
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        // TODO add your handling code here:
        jButton1.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton83MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton83MousePressed
        // TODO add your handling code here:
        jButton83.setBackground(new Color(242,242,242));
    }//GEN-LAST:event_jButton83MousePressed

    private void jButton83MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton83MouseReleased
        // TODO add your handling code here:
        jButton83.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton83MouseReleased

    private void jDialog1WindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowLostFocus
        // TODO add your handling code here:
        jDialog1.dispose(); inbox=!inbox; active.setBackground(Color.WHITE);
    }//GEN-LAST:event_jDialog1WindowLostFocus

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        // TODO add your handling code here:
        jButton1.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        // TODO add your handling code here:
        jButton1.setBackground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton83MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton83MouseEntered
        // TODO add your handling code here:
        jButton83.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_jButton83MouseEntered

    private void jButton83MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton83MouseExited
        // TODO add your handling code here:
        jButton83.setBackground(Color.WHITE);
    }//GEN-LAST:event_jButton83MouseExited

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
        //System.out.println(evt.getNewState());
        if(!this.isEnabled()) return;
        switch(evt.getNewState()){
            case 1 : 
            case 7 : if(inbox) inbox=!inbox; //System.out.println("yeah"); 
                        jDialog1.setVisible(false); break;
            case 0 : if(evt.getOldState()!=1||!inbox){ inbox=false; break; } jDialog1.setVisible(true);
            case 6 : if(evt.getOldState()!=7||!inbox){ inbox=false; break; } jDialog1.setVisible(true);
        }
        //jDialog1.setVisible(false);
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        //System.out.println("yup "+inbox);
        //if(inbox) jDialog1.setVisible(true); //inwindow=true;
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        // TODO add your handling code here:
        
        //if(inbox) inbox=!inbox;
        //System.out.println("yup");
    }//GEN-LAST:event_formWindowLostFocus

    private void jDialog1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDialog1FocusLost
        // TODO add your handling code here:
        //jDialog1.dispose(); inbox=!inbox;
        //System.out.println("no "+inbox);
    }//GEN-LAST:event_jDialog1FocusLost

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        // TODO add your handling code here:
        /*java.awt.Point pt=new java.awt.Point(this.getMousePosition());
        SwingUtilities.convertPointToScreen(pt, this);
        java.awt.Point pt1=new java.awt.Point(jButton60.getLocation());
        SwingUtilities.convertPointToScreen(pt1, jButton60);
        //System.out.println(pt.getX()+" "+pt1.getX());
        if(pt1.getX()-pt.getX()>=218){
            jDialog1.setBounds((int)pt.getX()-215, (int)pt.getY()-115, 214, 240);
            jLabel1.setText(" "); jLabel2.setText("  "); jLabel3.setText(">");
        }
        else{
            jDialog1.setBounds((int)pt.getX(), (int)pt.getY()-115, 214, 240);
            jLabel1.setText(" "); jLabel3.setText(" "); jLabel2.setText("<");
        }
        //System.out.println(pt.getX()+" "+this.getX()+" "+(int)getMousePosition().getX());
        //jDialog1.setBounds(this.getX()+840, this.getY()+(int)getMousePosition().getY()-115, 214, 240);
        jDialog1.setVisible(true); inbox=true;*/
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton99ActionPerformed
        // TODO add your handling code here:
        /*active.setText("1"); active.setFont(new Font("Liberation Sans",1,15));
        inbox=false; active.setBackground(Color.white); active.setForeground(Color.BLACK);
        jDialog1.dispose();*/
    }//GEN-LAST:event_jButton99ActionPerformed

    private void jButton84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton84ActionPerformed
        // TODO add your handling code here:
        /*newgame=true; jDialog2.dispose();
        this.setEnabled(true); inbox=false;
        for(int i=0;i<9;i++){
            for(int j=1;j<10;j++){
                Inrow[i][j]=0; //new HashSet<Integer>();
                Incol[i][j]=0;  //new HashSet<Integer>();
                Inbox[i][j]=0;
            }
        }
        K=1; //44+(int)(Math.random()*11);
        generator=new Generate_Sudoku(9,K);
        generator.fillValues();
        generator.printSudoku(generator.solved);
        addGridActionListeners();
        addInboxListeners();*/
        Timer.stop();
        this.dispose();
        Sudoku sudoku=new Sudoku();
        sudoku.requestFocusInWindow();
        sudoku.setVisible(true);
    }//GEN-LAST:event_jButton84ActionPerformed

    private void jDialog2WindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog2WindowClosed
        // TODO add your handling code here:
        this.dispose();
        //if(!newgame){ jDialog2.dispose(); this.dispose(); } newgame=false; 
    }//GEN-LAST:event_jDialog2WindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane jp=new javax.swing.JOptionPane("Do you");
        
        //jOptionPane1.setSize(300, 100);
        //jOptionPane1.setVisible(true);
        
 
        String[] buttons = { "Yes", "No" };

        int rc = JOptionPane.showOptionDialog(null, jPanel6, "",
                    JOptionPane.PLAIN_MESSAGE, -1, null, buttons, null);
        //System.out.println(rc);
        if(rc==0){
            for(int i=0;i<9;i++){
                for(int j=1;j<10;j++){
                    Inrow[i][j]=0; //new HashSet<Integer>();
                    Incol[i][j]=0;  //new HashSet<Integer>();
                    Inbox[i][j]=0;
                }
            }
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){ 
                    if(grid[i][j].getBackground()==Color.WHITE&&!grid[i][j].getText().equals(" ")){
                        grid[i][j].setText(" ");
                    }
                    if(grid[i][j].getBackground()==Color.RED){ 
                        grid[i][j].setBackground(new Color(96,130,182));
                        int cell;
                        cell = Integer.parseInt(grid[i][j].getText());
                        Inrow[i][cell]++; Incol[j][cell]++; Inbox[i/3*3+j/3][cell]++;
                    }            
                }
            }
            this.K=this.generator.K;
        }
        //javax.swing.JOptionPane.showMessageDialog(this, jPanel6, "message", javax.swing.JOptionPane.DEFAULT_OPTION);
        //int response = javax.swing.JOptionPane.showConfirmDialog(this, jPanel6, "", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton86ActionPerformed
        // TODO add your handling code here:
        jDialog2.dispose();
    }//GEN-LAST:event_jButton86ActionPerformed

    private void jButton83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton83ActionPerformed
        // TODO add your handling code here:
        String[] buttons = { "Yes", "No" };

        int rc = JOptionPane.showOptionDialog(null, jPanel7, "",
                    JOptionPane.PLAIN_MESSAGE, -1, null, buttons, null);
        if(rc==0){
            Timer.stop();
            this.dispose();
            Sudoku sudoku=new Sudoku();
            sudoku.setVisible(true);
        }
    }//GEN-LAST:event_jButton83ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if(jDialog3.isVisible()){ jEditorPane1.grabFocus(); return; }
        String[] buttons = { "Yes", "No" };

        int rc = JOptionPane.showOptionDialog(null, jPanel7, "",
                    JOptionPane.PLAIN_MESSAGE, -1, null, buttons, null);
        if(rc==0){ Timer.stop(); this.dispose(); }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        /*System.out.println();*/
        jDialog3.setSize(600, 450);
        jDialog3.setLocationRelativeTo(this);
        jDialog3.setVisible(true);
    }//GEN-LAST:event_formWindowOpened

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        jDialog3.setOpacity(0.35f); 
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        jDialog3.setOpacity(1f);
    }//GEN-LAST:event_formMouseReleased

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        //jDialog3.dispose();
        jEditorPane1.setText(""); jEditorPane1.setBackground(new Color(255,255,240));
        jPanel10.setVisible(true); //jPanel8.setVisible(false); 
        jPanel9.setVisible(false);
        //jScrollPane1.setBorder(null);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        jDialog3.dispose();
        this.setContentPane(contentpane);
        Timer.start();
        //BlurLayerUI layerUI = new BlurLayerUI(false);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        jDialog3.dispose(); this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jEditorPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jEditorPane1FocusGained
        // TODO add your handling code here:
        //System.out.println("yes");
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202,202,212),2));
                //new java.awt.Color(191, 188, 188), 2));
    }//GEN-LAST:event_jEditorPane1FocusGained

    private void jEditorPane1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jEditorPane1FocusLost
        // TODO add your handling code here:
        //System.out.println("no");
        jScrollPane1.setBorder(border);
    }//GEN-LAST:event_jEditorPane1FocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sudoku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sudoku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton100;
    private javax.swing.JButton jButton101;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton79;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton80;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JButton jButton83;
    private javax.swing.JButton jButton84;
    private javax.swing.JButton jButton86;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton93;
    private javax.swing.JButton jButton94;
    private javax.swing.JButton jButton95;
    private javax.swing.JButton jButton96;
    private javax.swing.JButton jButton97;
    private javax.swing.JButton jButton98;
    private javax.swing.JButton jButton99;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
