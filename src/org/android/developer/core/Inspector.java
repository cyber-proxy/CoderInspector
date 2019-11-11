package org.android.developer.core;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.inheritanceToDelegation.usageInfo.ObjectUpcastedUsageInfo;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import org.android.developer.others.NotifyUtils;
import org.android.developer.util.LogUtil;
import org.android.developer.util.ParseAllFile;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LC
 * @des
 * @Reference https://blog.csdn.net/nanjizhiyin/article/details/89452297
 * @Reference https://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview/modifying_psi.html 修改源代码。
 */
public class Inspector {
    List<PsiField> fields = new ArrayList<>();
    List<UsageInfo> usageInfoList = new ArrayList<>();
    public static String mDecryptFunction;

    public void execute(PsiFile psiFile, Project prj) {
        fields.addAll(ParseAllFile.gerVariablesList(psiFile));
        PsiElementFactory psiElementFactory = JavaPsiFacade.getInstance(prj).getElementFactory();
        for (PsiField psiField : fields) {
            List<PsiElement> list = ParseAllFile.findUsage(psiField, prj);
            for (PsiElement psiElement : list){
                if (!psiElement.getParent().getParent().getText().startsWith(mDecryptFunction)){
                    LogUtil.e("found errors: " + psiElement.getContainingFile().getName());
                    PsiElement psiElementNew = psiElementFactory.createExpressionFromText(mDecryptFunction + "(" + psiElement.getText() + ")", psiElement);
                    WriteCommandAction.runWriteCommandAction(prj, new Runnable() {
                        @Override
                        public void run() {
                            psiElement.replace(psiElementNew);
                        }
                    });
                }
            }
        }
    }

    /**
     * 遍历工程，获取各个类文件
     *
     * @param project
     */
    private void parsePrj(Project project) {
        LogUtil.log("parsePrj->" + project.getName());
        Processor<PsiClass> processor = new Processor<PsiClass>() {
            @Override
            public boolean process(PsiClass psiClass) {
                parseFile(psiClass, project);
                return true;
            }
        };

        AllClassesGetter.processJavaClasses(
                new PlainPrefixMatcher(""),
                project,
                GlobalSearchScope.projectScope(project),
                processor
        );

//        for (UsageInfo info : usageInfoList){
//            LogUtil.log("usage info -> " + info.toString());
//        }
//        NotifyUtils.showInfo(project, "Parse completed, please see logs for details.");
    }


    private void parseFile(PsiClass psiClass, Project project) {
        LogUtil.log("parse->" + psiClass.getName());
        for (PsiField field : fields) {
//            ParseAllFile.findUsage(field, project);
        }
    }
}
