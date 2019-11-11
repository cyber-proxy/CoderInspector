package org.android.developer.core;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import org.android.developer.ui.NotifyUtils;
import org.android.developer.util.LogUtil;
import org.android.developer.util.ParseAllFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @des
 * @Reference https://blog.csdn.net/nanjizhiyin/article/details/89452297
 * @Reference https://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview/modifying_psi.html 修改源代码。
 */
public class Inspector {
    List<PsiField> fields = new ArrayList<>();

    public void execute(PsiFile psiFile, Project prj, String decryptFunName) {
        fields.addAll(ParseAllFile.gerVariablesList(psiFile));
        PsiElementFactory psiElementFactory = JavaPsiFacade.getInstance(prj).getElementFactory();
        for (PsiField psiField : fields) {
            List<PsiElement> list = ParseAllFile.findUsage(psiField, prj);
            for (PsiElement psiElement : list){
                if (!psiElement.getParent().getParent().getText().startsWith(decryptFunName) && !psiElement.getParent().getText().contains("import")){
                    LogUtil.e("found errors: " + psiElement.getContainingFile().getName());
                    PsiElement psiElementNew = psiElementFactory.createExpressionFromText(decryptFunName + "(" + psiElement.getText() + ")", psiElement);
                    WriteCommandAction.runWriteCommandAction(prj, new Runnable() {
                        @Override
                        public void run() {
                            psiElement.replace(psiElementNew);
                        }
                    });
                }
            }
        }
        NotifyUtils.showInfo(prj, "检查完毕，请commit一下，查看已经修改的内容，（详情请打开Help->show log in explorer->idea.log文件查看日志。");
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
