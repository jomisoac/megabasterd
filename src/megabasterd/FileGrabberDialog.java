package megabasterd;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import static java.util.logging.Logger.getLogger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import static megabasterd.MainPanel.FONT_DEFAULT;
import static megabasterd.MainPanel.THREAD_POOL;
import static megabasterd.MiscTools.BASE642Bin;
import static megabasterd.MiscTools.Bin2BASE64;
import static megabasterd.MiscTools.bin2i32a;
import static megabasterd.MiscTools.deleteAllExceptSelectedTreeItems;
import static megabasterd.MiscTools.deleteSelectedTreeItems;
import static megabasterd.MiscTools.formatBytes;
import static megabasterd.MiscTools.genID;
import static megabasterd.MiscTools.sortTree;
import static megabasterd.MiscTools.swingReflectionInvoke;
import static megabasterd.MiscTools.swingReflectionInvokeAndWait;
import static megabasterd.MiscTools.swingReflectionInvokeAndWaitForReturn;
import static megabasterd.MiscTools.updateFont;

/**
 *
 * @author tonikelope
 */
public final class FileGrabberDialog extends javax.swing.JDialog {

    private boolean _upload;
    private final ArrayList<File> _files;
    private String _base_path;
    private long _total_space;
    private volatile String _last_selected_account;
    private final MainPanel _main_panel;
    private boolean _remember_master_pass;

    public boolean isUpload() {
        return _upload;
    }

    public ArrayList<File> getFiles() {
        return _files;
    }

    public String getBase_path() {
        return _base_path;
    }

    public JComboBox<String> getAccount_combobox() {
        return account_combobox;
    }

    public JTextField getDir_name_textfield() {
        return dir_name_textfield;
    }

    public boolean isRemember_master_pass() {
        return _remember_master_pass;
    }

    /**
     * Creates new form FileGrabber
     *
     * @param parent
     * @param modal
     */
    public FileGrabberDialog(java.awt.Frame parent, boolean modal) {

        super(parent, modal);

        initComponents();

        _last_selected_account = null;
        _total_space = 0L;
        _base_path = null;
        _upload = false;
        _remember_master_pass = true;
        _files = new ArrayList<>();

        MiscTools.swingInvokeIt(new Runnable() {

            @Override
            public void run() {
                updateFont(dance_button, FONT_DEFAULT, Font.PLAIN);
                updateFont(add_files_button, FONT_DEFAULT, Font.PLAIN);
                updateFont(add_folder_button, FONT_DEFAULT, Font.PLAIN);
                updateFont(account_combobox, FONT_DEFAULT, Font.PLAIN);
                updateFont(dir_name_label, FONT_DEFAULT, Font.PLAIN);
                updateFont(account_label, FONT_DEFAULT, Font.PLAIN);
                updateFont(used_space_label, FONT_DEFAULT, Font.BOLD);
                updateFont(total_file_size_label, FONT_DEFAULT, Font.BOLD);
                updateFont(file_tree, FONT_DEFAULT, Font.PLAIN);
                updateFont(warning_label, FONT_DEFAULT, Font.PLAIN);
                updateFont(dir_name_textfield, FONT_DEFAULT, Font.PLAIN);
                updateFont(skip_button, FONT_DEFAULT, Font.PLAIN);
                updateFont(skip_rest_button, FONT_DEFAULT, Font.PLAIN);
            }
        }, true);

        swingReflectionInvokeAndWait("addMouseListener", dir_name_textfield, new ContextMenuMouseListener());

        _main_panel = ((MainPanelView) parent).getMain_panel();

        if (_main_panel.getMega_accounts().size() > 0) {

            for (Object o : _main_panel.getMega_accounts().keySet()) {

                swingReflectionInvoke("addItem", account_combobox, o);
            }

        } else {
            swingReflectionInvoke("setForeground", used_space_label, Color.red);
            swingReflectionInvoke("setText", used_space_label, "No MEGA accounts available (Go to Settings > Accounts)");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        file_tree_scrollpane = new javax.swing.JScrollPane();
        file_tree = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        dir_name_label = new javax.swing.JLabel();
        dir_name_textfield = new javax.swing.JTextField();
        account_label = new javax.swing.JLabel();
        account_combobox = new javax.swing.JComboBox<>();
        used_space_label = new javax.swing.JLabel();
        add_folder_button = new javax.swing.JButton();
        add_files_button = new javax.swing.JButton();
        dance_button = new javax.swing.JButton();
        total_file_size_label = new javax.swing.JLabel();
        warning_label = new javax.swing.JLabel();
        skip_rest_button = new javax.swing.JButton();
        skip_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FileGrabber");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Files"));

        file_tree.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        file_tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        file_tree.setDoubleBuffered(true);
        file_tree.setEnabled(false);
        file_tree.setRootVisible(false);
        file_tree_scrollpane.setViewportView(file_tree);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(file_tree_scrollpane)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(file_tree_scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );

        dir_name_label.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        dir_name_label.setText("Upload name:");
        dir_name_label.setDoubleBuffered(true);
        dir_name_label.setEnabled(false);

        dir_name_textfield.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        dir_name_textfield.setDoubleBuffered(true);
        dir_name_textfield.setEnabled(false);

        account_label.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        account_label.setText("Account:");
        account_label.setDoubleBuffered(true);
        account_label.setEnabled(false);

        account_combobox.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        account_combobox.setDoubleBuffered(true);
        account_combobox.setEnabled(false);
        account_combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                account_comboboxItemStateChanged(evt);
            }
        });

        used_space_label.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        used_space_label.setText("Used space: 0.00GB");
        used_space_label.setDoubleBuffered(true);

        add_folder_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        add_folder_button.setText("Add folder");
        add_folder_button.setDoubleBuffered(true);
        add_folder_button.setEnabled(false);
        add_folder_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_folder_buttonActionPerformed(evt);
            }
        });

        add_files_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        add_files_button.setText("Add files");
        add_files_button.setDoubleBuffered(true);
        add_files_button.setEnabled(false);
        add_files_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_files_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dir_name_label)
                    .addComponent(account_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(add_files_button, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add_folder_button, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dir_name_textfield)
                    .addComponent(account_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(used_space_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dir_name_label)
                    .addComponent(dir_name_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(account_label)
                    .addComponent(account_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(used_space_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_files_button)
                    .addComponent(add_folder_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dance_button.setBackground(new java.awt.Color(102, 204, 255));
        dance_button.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        dance_button.setForeground(new java.awt.Color(255, 255, 255));
        dance_button.setText("Let's dance, baby");
        dance_button.setDoubleBuffered(true);
        dance_button.setEnabled(false);
        dance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dance_buttonActionPerformed(evt);
            }
        });

        total_file_size_label.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        total_file_size_label.setText("[0 B]");
        total_file_size_label.setDoubleBuffered(true);
        total_file_size_label.setEnabled(false);

        warning_label.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        warning_label.setText("If you DO NOT want to upload some folder or file you can REMOVE it.");
        warning_label.setDoubleBuffered(true);
        warning_label.setEnabled(false);

        skip_rest_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        skip_rest_button.setText("REMOVE ALL EXCEPT THIS");
        skip_rest_button.setDoubleBuffered(true);
        skip_rest_button.setEnabled(false);
        skip_rest_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skip_rest_buttonActionPerformed(evt);
            }
        });

        skip_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        skip_button.setText("REMOVE THIS");
        skip_button.setDoubleBuffered(true);
        skip_button.setEnabled(false);
        skip_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skip_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(warning_label)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(skip_rest_button)
                                .addGap(18, 18, 18)
                                .addComponent(skip_button)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dance_button, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(total_file_size_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_file_size_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(warning_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(skip_rest_button)
                    .addComponent(skip_button))
                .addGap(18, 18, 18)
                .addComponent(dance_button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void add_files_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_files_buttonActionPerformed

        add_files_button.setText("Adding files, please wait...");
        add_files_button.setEnabled(false);
        add_folder_button.setEnabled(false);
        warning_label.setEnabled(false);
        skip_button.setEnabled(false);
        skip_rest_button.setEnabled(false);
        dance_button.setEnabled(false);
        dir_name_textfield.setEnabled(false);
        dir_name_label.setEnabled(false);

        JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setDialogTitle("Add files");

        filechooser.setAcceptAllFileFilterUsed(false);

        filechooser.setMultiSelectionEnabled(true);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            total_file_size_label.setText("[0 B]");

            File[] files_selected = filechooser.getSelectedFiles();

            _base_path = files_selected[0].getParentFile().getAbsolutePath();

            System.out.println(_base_path);

            dir_name_textfield.setText(files_selected[0].getParentFile().getName() + "_" + genID(10));

            dir_name_textfield.setEnabled(true);

            dir_name_label.setEnabled(true);

            DefaultMutableTreeNode root = new DefaultMutableTreeNode(filechooser.getSelectedFile().getParent());

            for (File file : files_selected) {

                DefaultMutableTreeNode current_file = new DefaultMutableTreeNode(file.getName() + (file.isFile() ? " [" + formatBytes(file.length()) + "]" : ""));

                root.add(current_file);
            }

            DefaultTreeModel tree_model = new DefaultTreeModel(sortTree(root));

            file_tree.setModel(tree_model);

            _genFileList();

            add_files_button.setEnabled(true);

            add_folder_button.setEnabled(true);

            add_files_button.setText("Add files");

            boolean root_childs = ((TreeNode) tree_model.getRoot()).getChildCount() > 0;

            file_tree.setRootVisible(root_childs);
            file_tree.setEnabled(root_childs);
            warning_label.setEnabled(root_childs);
            dance_button.setEnabled(root_childs);
            total_file_size_label.setEnabled(root_childs);
            skip_button.setEnabled(root_childs);
            skip_rest_button.setEnabled(root_childs);

        } else {

            add_files_button.setText("Add files");
            add_files_button.setEnabled(true);
            add_folder_button.setEnabled(true);
            warning_label.setEnabled(true);
            skip_button.setEnabled(true);
            skip_rest_button.setEnabled(true);
            dance_button.setEnabled(true);
            dir_name_textfield.setEnabled(true);
            dir_name_label.setEnabled(true);
        }
    }//GEN-LAST:event_add_files_buttonActionPerformed

    private void add_folder_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_folder_buttonActionPerformed

        add_folder_button.setText("Adding files, please wait...");

        add_files_button.setEnabled(false);
        add_folder_button.setEnabled(false);
        warning_label.setEnabled(false);
        skip_button.setEnabled(false);
        skip_rest_button.setEnabled(false);
        dance_button.setEnabled(false);
        dir_name_textfield.setEnabled(false);
        dir_name_label.setEnabled(false);

        JFileChooser filechooser = new javax.swing.JFileChooser();

        filechooser.setDialogTitle("Add directory");

        filechooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        filechooser.setAcceptAllFileFilterUsed(false);

        if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            total_file_size_label.setText("[0 B]");

            _base_path = filechooser.getSelectedFile().getAbsolutePath();

            System.out.println(_base_path);

            dir_name_textfield.setText(filechooser.getSelectedFile().getName() + "_" + genID(10));

            dir_name_textfield.setEnabled(true);

            dir_name_label.setEnabled(true);

            DefaultMutableTreeNode root = new DefaultMutableTreeNode(filechooser.getSelectedFile().getAbsolutePath());

            _genFileTree(filechooser.getSelectedFile().getAbsolutePath(), root);

            DefaultTreeModel tree_model = new DefaultTreeModel(sortTree(root));

            file_tree.setModel(tree_model);

            _genFileList();

            add_files_button.setEnabled(true);

            add_folder_button.setEnabled(true);

            add_folder_button.setText("Add folder");

            boolean root_childs = ((TreeNode) tree_model.getRoot()).getChildCount() > 0;

            file_tree.setRootVisible(root_childs);

            file_tree.setRootVisible(root_childs);
            file_tree.setEnabled(root_childs);
            warning_label.setEnabled(root_childs);
            dance_button.setEnabled(root_childs);
            total_file_size_label.setEnabled(root_childs);
            skip_button.setEnabled(root_childs);
            skip_rest_button.setEnabled(root_childs);

        } else {
            add_folder_button.setText("Add folder");
            add_files_button.setEnabled(true);
            add_folder_button.setEnabled(true);
            warning_label.setEnabled(true);
            skip_button.setEnabled(true);
            skip_rest_button.setEnabled(true);
            dance_button.setEnabled(true);
            dir_name_textfield.setEnabled(true);
            dir_name_label.setEnabled(true);

        }

    }//GEN-LAST:event_add_folder_buttonActionPerformed

    private void dance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dance_buttonActionPerformed

        _upload = true;

        this.setVisible(false);
    }//GEN-LAST:event_dance_buttonActionPerformed

    private void account_comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_account_comboboxItemStateChanged

        String selected_item = (String) account_combobox.getSelectedItem();

        if (selected_item != null && !selected_item.equals(_last_selected_account)) {

            _last_selected_account = selected_item;

            final String email = selected_item;

            final Dialog tthis = this;

            used_space_label.setForeground(Color.black);

            used_space_label.setText("Checking account quota, please wait...");

            account_combobox.setEnabled(false);
            account_label.setEnabled(false);
            dance_button.setEnabled(false);
            add_files_button.setEnabled(false);
            add_folder_button.setEnabled(false);
            dir_name_textfield.setEnabled(false);
            total_file_size_label.setEnabled(false);
            skip_button.setEnabled(false);
            skip_rest_button.setEnabled(false);
            warning_label.setEnabled(false);
            file_tree.setEnabled(false);

            THREAD_POOL.execute(new Runnable() {
                @Override
                public void run() {

                    HashMap<String, Object> account_info = (HashMap) _main_panel.getMega_accounts().get(email);

                    Long[] quota = null;

                    MegaAPI ma = _main_panel.getMega_active_accounts().get(account_combobox.getSelectedItem());

                    if (ma == null) {

                        ma = new MegaAPI();

                        String password_aes, user_hash;

                        try {

                            if (_main_panel.getMaster_pass_hash() != null) {

                                if (_main_panel.getMaster_pass() == null) {

                                    GetMasterPasswordDialog dialog = new GetMasterPasswordDialog((Frame) getParent(), true, _main_panel.getMaster_pass_hash(), _main_panel.getMaster_pass_salt());

                                    swingReflectionInvokeAndWait("setLocationRelativeTo", dialog, tthis);

                                    swingReflectionInvokeAndWait("setVisible", dialog, true);

                                    if (dialog.isPass_ok()) {

                                        _main_panel.setMaster_pass(dialog.getPass());

                                        dialog.deletePass();

                                        _remember_master_pass = dialog.getRemember_checkbox().isSelected();

                                        dialog.dispose();

                                        password_aes = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("password_aes")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                        user_hash = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("user_hash")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                    } else {

                                        dialog.dispose();

                                        throw new Exception();
                                    }

                                } else {

                                    password_aes = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("password_aes")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                    user_hash = Bin2BASE64(CryptTools.aes_cbc_decrypt_pkcs7(BASE642Bin((String) account_info.get("user_hash")), _main_panel.getMaster_pass(), CryptTools.AES_ZERO_IV));

                                }

                            } else {

                                password_aes = (String) account_info.get("password_aes");

                                user_hash = (String) account_info.get("user_hash");
                            }

                            ma.fastLogin(email, bin2i32a(BASE642Bin(password_aes)), user_hash);

                            _main_panel.getMega_active_accounts().put(email, ma);

                            quota = ma.getQuota();

                        } catch (Exception ex) {

                            getLogger(FileGrabberDialog.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                        quota = ma.getQuota();
                    }

                    if (quota != null) {

                        if (quota[0] <= Math.round((double) quota[1] / 2)) {

                            swingReflectionInvoke("setForeground", used_space_label, new Color(0, 128, 0));

                        } else if (quota[0] < quota[1]) {

                            swingReflectionInvoke("setForeground", used_space_label, new Color(230, 115, 0));

                        } else {

                            swingReflectionInvoke("setForeground", used_space_label, Color.red);
                        }

                        boolean root_childs = ((TreeNode) ((TreeModel) swingReflectionInvokeAndWaitForReturn("getModel", file_tree)).getRoot()).getChildCount() > 0;

                        swingReflectionInvoke("setText", used_space_label, formatBytes(quota[0]) + " / " + formatBytes(quota[1]));

                        swingReflectionInvoke("setEnabled", new Object[]{add_files_button, add_folder_button, account_combobox, account_label}, true);

                        swingReflectionInvoke("setEnabled", new Object[]{dir_name_textfield, dir_name_label, warning_label, dance_button, file_tree, total_file_size_label, skip_button, skip_rest_button}, root_childs);

                    } else {

                        _last_selected_account = null;

                        swingReflectionInvoke("setEnabled", account_combobox, true);

                        swingReflectionInvoke("setEnabled", account_label, true);

                        swingReflectionInvoke("setSelectedIndex", account_combobox, -1);

                        swingReflectionInvoke("setForeground", used_space_label, Color.red);

                        swingReflectionInvoke("setText", used_space_label, "ERROR checking account quota!");
                    }

                }
            });

        }

    }//GEN-LAST:event_account_comboboxItemStateChanged

    private void skip_rest_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skip_rest_buttonActionPerformed

        if (deleteAllExceptSelectedTreeItems(file_tree)) {

            _genFileList();

            boolean root_childs = ((TreeNode) file_tree.getModel().getRoot()).getChildCount() > 0;

            warning_label.setEnabled(root_childs);
            dance_button.setEnabled(root_childs);
            total_file_size_label.setEnabled(root_childs);
            skip_button.setEnabled(root_childs);
            skip_rest_button.setEnabled(root_childs);
            dir_name_textfield.setEnabled(root_childs);
            dir_name_label.setEnabled(root_childs);

            if (!root_childs) {

                dir_name_textfield.setText("");
            }

        }
    }//GEN-LAST:event_skip_rest_buttonActionPerformed

    private void skip_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skip_buttonActionPerformed

        if (deleteSelectedTreeItems(file_tree)) {

            _genFileList();

            boolean root_childs = ((TreeNode) file_tree.getModel().getRoot()).getChildCount() > 0;

            warning_label.setEnabled(root_childs);
            dance_button.setEnabled(root_childs);
            total_file_size_label.setEnabled(root_childs);
            skip_button.setEnabled(root_childs);
            skip_rest_button.setEnabled(root_childs);
            dir_name_textfield.setEnabled(root_childs);
            dir_name_label.setEnabled(root_childs);

            if (!root_childs) {

                dir_name_textfield.setText("");
            }
        }
    }//GEN-LAST:event_skip_buttonActionPerformed

    private void _genFileTree(String directoryName, DefaultMutableTreeNode root) {

        File directory = new File(directoryName);

        File[] fList = directory.listFiles();

        if (fList != null) {

            for (File file : fList) {

                if (file.isFile()) {

                    DefaultMutableTreeNode current_file = new DefaultMutableTreeNode(file.getName() + " [" + formatBytes(file.length()) + "]");

                    root.add(current_file);

                } else if (file.isDirectory() && file.listFiles().length > 0) {

                    DefaultMutableTreeNode current_dir = new DefaultMutableTreeNode(file.getName());

                    root.add(current_dir);

                    _genFileTree(file.getAbsolutePath(), current_dir);
                }
            }

        }
    }

    private void _genFileList() {

        _files.clear();

        _total_space = 0L;

        DefaultTreeModel tree_model = (DefaultTreeModel) file_tree.getModel();

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree_model.getRoot();

        Enumeration files_tree = root.depthFirstEnumeration();

        while (files_tree.hasMoreElements()) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) files_tree.nextElement();

            if (node.isLeaf() && node != root) {

                String path = "";

                Object[] object_path = node.getUserObjectPath();

                for (Object p : object_path) {

                    path += "/" + p;
                }

                path = path.replaceAll("^//", "/").trim().replaceAll(" \\[[0-9,]+ [A-Z]+\\]$", "");

                File file = new File(path);

                if (file.isFile()) {

                    _total_space += file.length();

                    _files.add(file);
                }
            }
        }

        total_file_size_label.setText("[" + formatBytes(_total_space) + "]");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> account_combobox;
    private javax.swing.JLabel account_label;
    private javax.swing.JButton add_files_button;
    private javax.swing.JButton add_folder_button;
    private javax.swing.JButton dance_button;
    private javax.swing.JLabel dir_name_label;
    private javax.swing.JTextField dir_name_textfield;
    private javax.swing.JTree file_tree;
    private javax.swing.JScrollPane file_tree_scrollpane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton skip_button;
    private javax.swing.JButton skip_rest_button;
    private javax.swing.JLabel total_file_size_label;
    private javax.swing.JLabel used_space_label;
    private javax.swing.JLabel warning_label;
    // End of variables declaration//GEN-END:variables
}
