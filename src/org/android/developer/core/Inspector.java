package org.android.developer.core;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.usageView.UsageInfo;
import org.android.developer.util.LogUtil;
import org.android.developer.util.ParseAllFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @des
 */
public class Inspector {
    public void execute(PsiFile psiFile, Project prj) {
        ParseAllFile.parsePrj(prj);
        List<UsageInfo> usageInfoList = new ArrayList<>();
        List<PsiField> fields =  ParseAllFile.gerVariablesList(psiFile);
        for (PsiField field : fields){
            usageInfoList.addAll(ParseAllFile.findUsages(field, field));
        }
        for (UsageInfo info : usageInfoList){
            LogUtil.log(info.toString());
        }
    }
}
