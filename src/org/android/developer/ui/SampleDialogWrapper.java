package org.android.developer.ui;

import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * @author LC
 * @des
 */
public class SampleDialogWrapper extends DialogWrapper {

    public SampleDialogWrapper() {
        super(true); // use current window as parent
        init();
        setTitle("代码检测");
        setSize(500, 200);
        setAutoAdjustable(false);
        setOKButtonText("开始检测");
        setCancelButtonText("取消");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new GridLayout(1,1));

        dialogPanel.add(new JLabel("请输入:"));
        dialogPanel.add(new JTextField("Security.decrypt"));
        return dialogPanel;
    }
}
