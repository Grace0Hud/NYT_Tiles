package edu.cpp.cs3560.view;
import edu.cpp.cs3560.control.GameController;
import edu.cpp.cs3560.model.BoardCell;
import edu.cpp.cs3560.model.GameModel;
import edu.cpp.cs3560.model.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Code to create the swing GUI.
 */
public class SwingUI
{
    private static final int ROWS = 4, COLS = 4; //number of rows and columns in the board.
    private final JFrame frame = new JFrame("New York Times Tile Match - Swing"); //global frame.
    private final JPanel grid = new JPanel(new GridLayout(ROWS, COLS, 8, 8)); //panel within frame for grid.
    private final JLabel status = new JLabel("Find all matches!");
    private final JButton[][] buttons = new JButton[ROWS][COLS]; //buttons in the grid.
    private final GameModel model; //the model of the game
    private final GameController controller; //controller for the game.
    private int difficulty = 1; //difficulty level.
    private final HashMap<String, Color> themeColors = new HashMap<>(){{
	  put("GREEN", new Color(66, 131, 49));
	  put("BROWN", new Color(78, 50, 15));
	  put("CREAM", new Color(218, 175, 131));
    }}; //theme colors used. Just makes for less copy pasting.

    private final Icon backIcon = iconColor(themeColors.get("GREEN"), "Matched!"); //icon used for matched pairs.
    private final Map<Integer, Icon> faceIcons = new HashMap<>(); //icons used for unmatched.

    /**
     * Constructs the frame for the swing UI.
     */
    public SwingUI() {
	  //opens a dialog box to chose the difficulty before beginning.
	  chooseDifficulty();
	   model = new GameModel(ROWS, COLS, System.nanoTime(),difficulty);
	   controller = new GameController(model);
	  //setup icons according to difficulty.
	  setUpIcons();
	  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  frame.setLayout(new BorderLayout(10,10));
	  JPanel top = new JPanel(new BorderLayout());
	  top.add(status, BorderLayout.WEST);
	  frame.add(top, BorderLayout.NORTH);
	  frame.add(grid, BorderLayout.CENTER);
	  grid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	  grid.setBackground(themeColors.get("BROWN"));
	  //build up the buttons.
	  buildGrid();
	  frame.setSize(600, 650);
	  frame.setLocationRelativeTo(null);
	  frame.getContentPane().setBackground(themeColors.get("BROWN"));
	  //show frame!
	  frame.setVisible(true);
    }

    /**
     * Sets up the icons according to difficulty selected.
     */
    private void setUpIcons()
    {
	  String[] glyphs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	  HashMap<Integer, String> cardGlyphs = new HashMap<>();
	  for(int i = 0; i < ROWS * COLS * difficulty; i++)
	  {
		cardGlyphs.put(i, glyphs[i%glyphs.length]);
	  }
		String glyph = "";
		int count = 0;
		for(int i = 0; i < ROWS; i++)
		{
		    for(int j = 0; j < COLS; j++)
		    {
			  faceIcons.put(count, iconColor(themeColors.get("CREAM"), getCellGlyph(i,j, cardGlyphs)));
			  count ++;
		    }
		}
    }

    private String getCellGlyph(int r, int c, HashMap<Integer, String> icons)
    {
	  String glyph = "";
	  Card[] cards = model.getBoard().getCellAt(r, c).getCards();
	  for (int k = 0; k < cards.length; k++)
	  {
		//System.out.println("Key: " + cards[k].getId());
		if(!cards[k].isMatched())
		    glyph += icons.get(cards[k].getId()) + " ";
	  }
	  //System.out.println("Glyph: " + glyph);
	  return glyph;
    }
    /**
     * Creates a grid of buttons to be used as the board.
     */
    private void buildGrid() {
	  for (int r=0;r<ROWS;r++) {
		for (int c=0;c<COLS;c++) {
		    JButton b = new JButton(faceIcons.get(model.getBoard().getCellAt(r,c).getId()));
		    b.setBackground(themeColors.get("CREAM"));
		    b.setFocusPainted(false);
		    int rr = r, cc = c;
		    b.addActionListener(e -> onClick(rr, cc));
		    buttons[r][c] = b;
		    grid.add(b);
		}
	  }
	  refresh();
    }

    /**
     * Called when a card is clicked. Handles game logic using the game controller.
     * @param r row of clicked card
     * @param c column of clicked card.
     */
    private void onClick(int r, int c) {
	  controller.onCardClicked(r, c, () -> {
		// Schedule a delay using Swing Timer
		new javax.swing.Timer(700, e -> {
		    BoardCell a = model.getBoard().getCellAt(r, c); // not the first; so we must find both
			// Find a non-matched partner (the firstPick was internal)
			// Simpler: just flip back all faceUp but not matched & not this one after mismatch
			// But we have explicit method: we need both; so scan
		    BoardCell first = null, second = null;
		    outer:
		    for (int i=0;i<model.getBoard().getRows();i++)
			  for (int j=0;j<model.getBoard().getCols();j++) {
				BoardCell cell = model.getBoard().getCellAt(i,j);
				if (cell.isSelected() && !cell.isAllMatched()) {
				    if (first == null) first = cell;
				    else { second = cell; break outer; }
				}
			  }
		    if (first != null && second != null) {
			  controller.resolveMismatch(first, second);
		    }
		    refresh();
		}) {{ setRepeats(false); }}.start();
	  });
	  refresh();
	  if (controller.isWin()) status.setText("You win! " + model.toString());
    }
    private void refresh() {
	  for (int r=0;r<ROWS;r++) for (int c=0;c<COLS;c++)
	  {
		BoardCell cell = model.getBoard().getCellAt(r,c);
		JButton b = buttons[r][c];
		if(cell.isAllMatched())
		{
		    b.setIcon(backIcon);
		}else if(cell.isSelected())
		{
		    b.setBorder(BorderFactory.createLineBorder(themeColors.get("GREEN"), 4));
		}else if(cell.isPartialMatched())
		{
		    b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		    setUpIcons();
		    b.setIcon(faceIcons.get(cell.getId()));
		}else
		{
		    b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	  }
	  status.setText("Difficulty: " + difficulty + "   " + model.toString());
    }

    /**
     * Helper function for setting up icons
     * @param bg the color of the background
     * @param text the text to put on the button.
     * @return a formatted icon.
     */
    private Icon iconColor(Color bg, String text) {
	  int W=120, H=150;
	  Image img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
	  Graphics2D g = (Graphics2D) img.getGraphics();
	  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
	  g.setColor(bg); g.fillRoundRect(0,0,W,H,20,20);
	  g.setColor(Color.BLACK);
	  g.setFont(new Font("SansSerif", Font.PLAIN, 25));
	  FontMetrics fm = g.getFontMetrics();
	  int tw = fm.stringWidth(text);
	  g.drawString(text, (W - tw)/2, H/2 + fm.getAscent()/2 - 8);
	  g.dispose();
	  return new ImageIcon(img);
    }

    /**
     * Opens up a dialog box to get the user to select difficulty from a drop down.
     */
    private void chooseDifficulty()
    {
	  JDialog dialog = new JDialog(frame, "Choose Difficulty", true);
	  dialog.setForeground(themeColors.get("CREAM"));
	  dialog.setSize(300, 100);
	  //creating a panel within the dialog to setup the visible selector.
	  JPanel panel = new JPanel();
	  dialog.setBackground(themeColors.get("BROWN"));
	  panel.setBackground(themeColors.get("BROWN"));
	  String[] options = {"Easy", "Medium", "Hard"};
	  JLabel label = new JLabel("Choose Difficulty");
	  label.setForeground(themeColors.get("CREAM"));
	  panel.add(label);
	  //creating a selector and adding the chosen options.
	  JComboBox selector = new JComboBox(options);
	  selector.setBackground(themeColors.get("CREAM"));
	  selector.setForeground(themeColors.get("GREEN"));
	  panel.add(selector);
	  //wainting for change. Defaults to easy.
	  selector.addItemListener(new ItemListener()
	  {
		@Override
		public void itemStateChanged(ItemEvent e) {
		    if(selector.getSelectedItem().equals("Hard"))
		    {
			  difficulty = 3;
		    }else if (selector.getSelectedItem().equals("Medium"))
		    {
			  difficulty = 2;
		    }
		}
	  });
	  //button to submit and move forward.
	  JButton ok = new JButton("OK");
	  ok.setBackground(themeColors.get("GREEN"));
	  ok.addActionListener(e -> {
		dialog.dispose();
	  });
	  //adding dialog to panel and setting visible.
	  panel.add(ok);
	  dialog.add(panel);
	  dialog.setVisible(true);
    }

    /**
     * Run that app!
     * @param args
     */
    public static void main(String[] args) {
	  SwingUtilities.invokeLater(SwingUI::new);
    }
}

