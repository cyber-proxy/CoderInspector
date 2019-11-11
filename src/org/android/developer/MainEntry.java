package org.android.developer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.android.developer.core.Inspector;
import org.android.developer.others.ClassDataWriter;
import org.android.developer.others.PsiFileUtils;
import org.android.developer.others.ResIdBean;
import org.android.developer.ui.SampleDialogWrapper;
import org.android.developer.util.LogUtil;
import org.android.developer.util.ParseAllFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @des
 */
public class MainEntry extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        LogUtil.log("actionPerformed");
        Editor editor = anActionEvent.getData(DataKeys.EDITOR);
        PsiFile psiFile = anActionEvent.getData(DataKeys.PSI_FILE);
        Project prj = anActionEvent.getData(DataKeys.PROJECT);
        new SampleDialogWrapper(psiFile, prj).showAndGet();
//
//        System.out.println("" + psiFile);
//        PsiElement psiElement = PsiFileUtils.getPsiElementByEditor(editor, psiFile);
//        if (editor != null && psiFile != null && psiElement != null) {
//            String name = String.format("%s.xml", psiElement.getText());
//            LogUtil.log("actionPerformed ->" + name);
//            PsiFile rootXmlFile = PsiFileUtils.getFileByName(psiElement, name);
//            if (rootXmlFile != null) {
//                ArrayList<ResIdBean> resIdBeans = new ArrayList<>();
//                PsiFileUtils.getResIdBeans(rootXmlFile, resIdBeans);
//                PsiClass psiClass = PsiFileUtils.getClassByClassFile(psiFile);
//                new ClassDataWriter(psiFile, resIdBeans, psiClass).execute();
//            }
//        }
    }
}
