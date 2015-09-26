package ??;
​
import PostFile;
​
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
​
public class TestHarnessUI extends javax.swing.JFrame
{
​
    /**
     * Creates new form TestHarnessUI
     */
    public TestHarnessUI()
    {
        initComponents();
    }
​
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
​
        jFileChooser1 = new javax.swing.JFileChooser();
        Input = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        Output = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
​
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
​
        Input.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SoundWave Test Harness", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 0, 18))); // NOI18N
        Input.setName("Input"); // NOI18N
​
        jButton1.setText("UPLOAD");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton1MouseClicked(evt);
            }
        });
​
        javax.swing.GroupLayout InputLayout = new javax.swing.GroupLayout(Input);
        Input.setLayout(InputLayout);
        InputLayout.setHorizontalGroup(
            InputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputLayout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        InputLayout.setVerticalGroup(
            InputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputLayout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 138, Short.MAX_VALUE))
        );
​
        Output.setName("Output"); // NOI18N
​
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
​
        javax.swing.GroupLayout OutputLayout = new javax.swing.GroupLayout(Output);
        Output.setLayout(OutputLayout);
        OutputLayout.setHorizontalGroup(
            OutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );
        OutputLayout.setVerticalGroup(
            OutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );
​
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Output, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Output, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
​
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
​
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseClicked
    {//GEN-HEADEREND:event_jButton1MouseClicked
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        jFileChooser1.setFileFilter(filter);
        int returnVal = jFileChooser1.showOpenDialog(Input);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            //String fileName = jFileChooser1.getSelectedFile().getName();
            String fileName = jFileChooser1.getSelectedFile().getPath();
            jTextArea1.setText("Uploading File: " + fileName);
            
            
            PostFile file = new PostFile();
            boolean success = file.uploadFile(fileName);
            if (success)
            {
                jTextArea1.append("\n" + fileName + " uploaded successfully!");
            }
            else jTextArea1.append("\n" + fileName + " upload FAILED!");
            
        }        
    }//GEN-LAST:event_jButton1MouseClicked
​
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(TestHarnessUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(TestHarnessUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(TestHarnessUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(TestHarnessUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
​
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new TestHarnessUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Input;
    private javax.swing.JPanel Output;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}