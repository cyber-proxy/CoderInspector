package org.android.developer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;

public class aaaaa extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Editor editor = anActionEvent.getData(DataKeys.EDITOR);
        PsiFile psiFile = anActionEvent.getData(DataKeys.PSI_FILE);
        PsiElement psiElement = PsiFileUtils.getPsiElementByEditor(editor, psiFile);
        if (editor != null && psiFile != null && psiElement != null) {
            String name = String.format("%s.xml", psiElement.getText());
            PsiFile rootXmlFile = PsiFileUtils.getFileByName(psiElement, name);
            if (rootXmlFile != null) {
                ArrayList<ResIdBean> resIdBeans = new ArrayList<>();
                PsiFileUtils.getResIdBeans(rootXmlFile, resIdBeans);
                PsiClass psiClass = PsiFileUtils.getClassByClassFile(psiFile);
                new ClassDataWriter(psiFile, resIdBeans, psiClass).execute();
            }
        }
    }
}
