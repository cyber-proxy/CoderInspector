package org.android.developer.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiFile;
import org.android.developer.core.Inspector;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author LC
 * @des
 */
public class SampleDialogWrapper extends DialogWrapper {
    private JTextField mInputField;
    private PsiFile psiFile ;
    private Project prj ;

    public SampleDialogWrapper(PsiFile psiFile, Project project) {
        super(true); // use current window as parent
        this.prj = project;
        this.psiFile = psiFile;
        init();
        setTitle("代码检测");
        setSize(500, 100);
        setResizable(false);
        setAutoAdjustable(false);
        setOKButtonText("开始检测");
        setCancelButtonText("取消");
        setOKActionEnabled(true);
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        Inspector.mDecryptFunction = mInputField.getText();
        new Inspector().execute(psiFile, prj);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new GridLayout(1,5));

        mInputField = new JTextField("Security.decrypt");

        dialogPanel.add(new JLabel("请输入:"));
        dialogPanel.add(mInputField);
        return dialogPanel;
    }
}
