package org.aesthete.swingobjects.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

/**
 * User: Sethuram
 * Date: 22/09/13
 * Time: 11:23 AM
 */
public class SwingObjFatTabbedPane extends JTabbedPane{
    private static final long	serialVersionUID	= 1L;

    private DataFlavor draggableTabbedPaneFlavor = null;

    private class JFatTabComponent extends JPanel
    {
        private static final long	serialVersionUID	= 1L;

        private boolean showPopOutButton = true;
        private boolean showCloseButton = true;

        private JFatTabComponent()
        {
            setOpaque(false);
            setBorder(new EmptyBorder(3, 0, 0, 0));
            FlowLayout flowLayout = (FlowLayout)getLayout();
            flowLayout.setVgap(0);
            flowLayout.setHgap(0);
        }
    }

    private class JTabbedPaneTransferable implements Transferable, Serializable
    {
        private static final long	serialVersionUID	= 1L;

        private SwingObjFatTabbedPane originPane;
        private Component component;
        private String title;
        private Icon icon;
        private String tip;
        private int index;
        private boolean didImport = false;

        private JTabbedPaneTransferable()
        {
            originPane = SwingObjFatTabbedPane.this;
            index = originPane.getSelectedIndex();
            component = originPane.getComponentAt( index );
            title = originPane.getTitleAt( index );
            icon = originPane.getIconAt( index );
            tip = originPane.getToolTipTextAt( index );
        }

        @Override
        public DataFlavor[] getTransferDataFlavors()
        {
            return new DataFlavor[] { draggableTabbedPaneFlavor };
        }

        @Override
        public boolean isDataFlavorSupported( DataFlavor flavor )
        {
            return draggableTabbedPaneFlavor.equals( flavor );
        }

        @Override
        public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException
        {
            if( !isDataFlavorSupported( flavor ) )
                throw new UnsupportedFlavorException( flavor );
            return this;
        }
    }

    private class JTabbedPaneHandler extends TransferHandler implements MouseInputListener, ChangeListener
    {
        private static final long	serialVersionUID	= 1L;

        @Override
        public void mouseClicked( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public void mousePressed( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public void mouseReleased( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public void mouseEntered( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public void mouseExited( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public void mouseDragged( MouseEvent e )
        {
            e.consume();
            if( e.getSource() == SwingObjFatTabbedPane.this )
            {
                int selectedIndex = SwingObjFatTabbedPane.this.getSelectedIndex();
                if( getTransferHandler() != null && selectedIndex != -1 )
                {
                    getTransferHandler().exportAsDrag( SwingObjFatTabbedPane.this, e, TransferHandler.MOVE );
                    SwingObjFatTabbedPane.this.removeTabAt( selectedIndex );
                }
            }
            else
            {
                System.out.println( "Unknown source "+e.getSource()+"!" );
            }
        }

        @Override
        public void mouseMoved( MouseEvent e )
        {
            e.consume();
        }

        @Override
        public int getSourceActions( JComponent component )
        {
            return MOVE;
        }

        @Override
        public Transferable createTransferable( JComponent c )
        {
            return new JTabbedPaneTransferable();
        }

        @Override
        public void exportDone( JComponent c, Transferable t, int action )
        {
            if( t instanceof JTabbedPaneTransferable )
            {
                JTabbedPaneTransferable data = (JTabbedPaneTransferable)t;
                if( !data.didImport )
                    data.originPane.insertTab( data.title, data.icon, data.component, data.tip, data.index );
            }
        }

        @Override
        public boolean canImport( TransferSupport support )
        {
            if( support.isDataFlavorSupported( draggableTabbedPaneFlavor ) )
                return true;
            return false;
        }

        @Override
        public boolean importData( TransferSupport support )
        {
            if( !canImport(support) )
                return false;
            JTabbedPaneTransferable d = null;
            try
            {
                d = (JTabbedPaneTransferable)support.getTransferable().getTransferData( draggableTabbedPaneFlavor );
            }
            catch( UnsupportedFlavorException ex )
            {
                throw new RuntimeException( ex );
            }
            catch( IOException ex )
            {
                throw new RuntimeException( ex );
            }
            final JTabbedPaneTransferable data = d;
            if( data != null )
            {
                SwingObjFatTabbedPane pane = SwingObjFatTabbedPane.this;
                int mouseX = pane.getMousePosition().x;
                int mouseY = pane.getMousePosition().y;
                int index = pane.indexAtLocation( mouseX, mouseY );
                if( index == -1 )
                {
                    index = pane.getTabCount();
                }
                else
                {
                    // Hack to find the width of the tab (technically, this is off by one pixel, but oh well)....
                    int tabX1;
                    int tabX2;
                    for( tabX1 = mouseX; pane.indexAtLocation(tabX1,mouseY) == index; tabX1-- );
                    for( tabX2 = mouseX; pane.indexAtLocation(tabX2,mouseY) == index; tabX2++ );
                    int tabCenter = tabX1 + (tabX2-tabX1) / 2;
                    // End hack.
                    if( mouseX > tabCenter )
                        index++;
                }
                pane.insertTab( data.title, data.icon, data.component, data.tip, index );
                pane.setSelectedComponent( data.component );
                data.didImport = true;
                return true;
            }
            return false;
        }

        @Override
        public void stateChanged( ChangeEvent e )
        {
            SwingObjFatTabbedPane pane = SwingObjFatTabbedPane.this;
            for( int i = 0; i < pane.getTabCount(); i++ )
                internalRefreshTabComponentAt( i );
        }
    }

    public SwingObjFatTabbedPane()
    {
        super();
        init();
    }

    public SwingObjFatTabbedPane( int tabPlacement )
    {
        super( tabPlacement );
        init();
    }

    public SwingObjFatTabbedPane( int tabPlacement, int tabLayoutPolicy )
    {
        super( tabPlacement, tabLayoutPolicy );
        init();
    }

    final private void init()
    {
        try
        {
            draggableTabbedPaneFlavor = new DataFlavor( DataFlavor.javaJVMLocalObjectMimeType + ";class=com.adamsoncomputing.acswinglib.SwingObjFatTabbedPane" );
        }
        catch( ClassNotFoundException ex )
        {
            throw new RuntimeException( ex );
        }

        JTabbedPaneHandler handler = new JTabbedPaneHandler();
        addMouseListener( handler );
        addMouseMotionListener( handler );
        setTransferHandler( handler );
        addChangeListener( handler );
    }

    @Override
    public void addTab( String title, Component component )
    {
        super.addTab( title, component );
        int index = indexOfComponent( component );
        createTabComponentAt( index );
    }

    @Override
    public void addTab( String title, Icon icon, Component component )
    {
        super.addTab( title, icon, component );
        int index = indexOfComponent( component );
        createTabComponentAt( index );
    }

    @Override
    public void addTab( String title, Icon icon, Component component, String tip )
    {
        super.addTab( title, icon, component, tip );
        int index = indexOfComponent( component );
        createTabComponentAt( index );
    }

    @Override
    public void insertTab( String title, Icon icon, Component component, String tip, int index )
    {
        super.insertTab( title, icon, component, tip, index );
        createTabComponentAt( index );
    }

    @Override
    public void setDisabledIconAt( int index, Icon disabledIcon )
    {
        super.setDisabledIconAt( index, disabledIcon );
        internalRefreshTabComponentAt( index );
    }

    @Override
    public void setIconAt( int index, Icon icon )
    {
        super.setIconAt( index, icon );
        internalRefreshTabComponentAt( index );
    }

    @Override
    public void setTitleAt( int index, String title )
    {
        super.setTitleAt( index, title );
        internalRefreshTabComponentAt( index );
    }

    final private void createTabComponentAt( int index )
    {
        super.setTabComponentAt( index, new JFatTabComponent() );
        internalRefreshTabComponentAt( index );
    }

    final private void internalRefreshTabComponentAt( final int index )
    {
        JFatTabComponent fatComponent = null;
        Component c = super.getTabComponentAt( index );
        if( c != null && c instanceof JFatTabComponent )
        {
            fatComponent = (JFatTabComponent)c;
            fatComponent.removeAll();

            if( SwingObjFatTabbedPane.this.getIconAt( index ) != null )
            {
                fatComponent.add( new JLabel( SwingObjFatTabbedPane.this.getIconAt( index ) ) );
                if( fatComponent.showPopOutButton || fatComponent.showCloseButton )
                    fatComponent.add( Box.createHorizontalStrut(8) );
            }

            if( SwingObjFatTabbedPane.this.getTitleAt( index ) != null )
            {
                fatComponent.add( new JLabel( SwingObjFatTabbedPane.this.getTitleAt( index ) ) );
                if( fatComponent.showPopOutButton || fatComponent.showCloseButton )
                    fatComponent.add( Box.createHorizontalStrut(8) );
            }

            Dimension buttonSize;
            int buttonSeparatorStrutSize;
            if( UIManager.getLookAndFeel().getName().toLowerCase().contains( "nimbus" ))
            {
                buttonSize = new Dimension(16, 16);
                buttonSeparatorStrutSize = 0;
            }
            else
            {
                buttonSize = new Dimension(12, 12);
                buttonSeparatorStrutSize = 2;
            }

            if( index == getSelectedIndex() )
            {
                if( fatComponent.showPopOutButton )
                {
                    JButton popOutButton = new JButton();
                    popOutButton.setFocusable(false);
                    popOutButton.setIcon(new ImageIcon(JFatTabComponent.class.getResource("/com/adamsoncomputing/acswinglib/buttonPopOut.png")));
                    popOutButton.setPreferredSize(buttonSize);
                    popOutButton.addActionListener( new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent e )
                        {
                            final String title = getTitleAt( index );
                            final Component content = getComponentAt( index );
                            int width = content.getWidth();
                            int height = content.getHeight();
                            SwingObjFatTabbedPane.this.removeTabAt( index );
                            final JFrame frame = new JFrame( title );
                            frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                            frame.setLocationByPlatform( true );
                            frame.setBounds( 100, 100, width, height );
                            final JPanel layoutPanel = new JPanel();
                            layoutPanel.setLayout( new BoxLayout( layoutPanel, BoxLayout.X_AXIS ) );
                            frame.setContentPane( layoutPanel );
                            layoutPanel.add( content );
                            frame.addWindowListener( new WindowAdapter()
                            {
                                @Override
                                public void windowClosing( WindowEvent event )
                                {
                                    layoutPanel.remove( content );
                                    SwingObjFatTabbedPane.this.addTab( title, content );
                                }
                            } );
                            frame.setVisible( true );
                        }
                    } );
                    fatComponent.add(popOutButton);
                }

                if( fatComponent.showPopOutButton && fatComponent.showCloseButton )
                {
                    if( buttonSeparatorStrutSize > 0 )
                        fatComponent.add( Box.createHorizontalStrut(buttonSeparatorStrutSize) );
                }

                if( fatComponent.showCloseButton )
                {
                    JButton closeButton = new JButton();
                    closeButton.setFocusable(false);
                    closeButton.setIcon(new ImageIcon(JFatTabComponent.class.getResource("/com/adamsoncomputing/acswinglib/buttonClose.png")));
                    closeButton.setPreferredSize(buttonSize);
                    closeButton.addActionListener( new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent e )
                        {
                            removeTabAt( index );
                        }
                    } );
                    fatComponent.add(closeButton);
                }
            }
        }
    }
}
