package org.android.developer.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author LC
 * @des
 */
public class CommonUtil {

    /**
     * 获取一个类的包信息
     *
     * @param psiClass
     * @param project
     * @return
     */
    public static PsiPackage getPkg(PsiClass psiClass, Project project) {
        PsiJavaFile javaFile = (PsiJavaFile) psiClass.getContainingFile();
        PsiPackage pkg = JavaPsiFacade.getInstance(project).findPackage(javaFile.getPackageName());
        return pkg;
    }

    public static String getLineNum(PsiClass psiClass, Project project) {
        String lineNum = "1";
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiClass.getContainingFile());
        return lineNum;
    }

    /**
     * @param psiElement:元素
     * @return
     * @author gaojindan
     * @date 2019/3/11 0011 17:08
     * @des 检查If语句规则
     */
    private void checkIfStatement(Document document, PsiElement psiElement) {
        PsiElement[] psiElements = psiElement.getChildren();
        for (PsiElement element : psiElements) {
            // 如果是if语句
            if (element instanceof PsiIfStatement) {
                PsiIfStatement psiIfStatement = (PsiIfStatement) element;
                PsiStatement thenSt = psiIfStatement.getThenBranch();
                String thenText = thenSt.getText();
                // 没有大括号
                if (!thenText.startsWith("{")) {
                    int lineNumbers = document.getLineNumber(thenSt.getTextOffset());
                    String text = "if后面没有使用大括号(line " + (lineNumbers) + ")";
                    DefaultMutableTreeNode tmpTreeNode = new DefaultMutableTreeNode(text);
                    ifNode.add(tmpTreeNode);
                }
                // else语句
                PsiStatement elseSt = psiIfStatement.getElseBranch();
                if (elseSt != null) {
                    // else语句
                    if (elseSt instanceof PsiExpressionStatement) {
                        if (!elseSt.getText().startsWith("{")) {
                            int lineNumbers = document.getLineNumber(elseSt.getTextOffset());
                            String text = "else 后面没有使用大括号(line " + (lineNumbers) + ")";
                            DefaultMutableTreeNode tmpTreeNode = new DefaultMutableTreeNode(text);
                            ifNode.add(tmpTreeNode);
                        }
                    }
                    // else if语句
                    if (elseSt instanceof PsiIfStatement) {
                        PsiIfStatement elseIfSt = (PsiIfStatement) elseSt;
                        thenSt = elseIfSt.getThenBranch();
                        thenText = thenSt.getText();
                        // 没有大括号
                        if (!thenText.startsWith("{")) {
                            int lineNumbers = document.getLineNumber(thenSt.getTextOffset());
                            String text = "if后面没有使用大括号(line " + (lineNumbers) + ")";
                            DefaultMutableTreeNode tmpTreeNode = new DefaultMutableTreeNode(text);
                            ifNode.add(tmpTreeNode);
                        }
                    }
                    // 递归else内容
                    checkIfStatement(document, elseSt);
                }

                // 递归if语句内容
                checkIfStatement(document, thenSt);
            }
        }
    }

    public long getLineNumberByIo(String str) {
        LineNumberReader lnr = new LineNumberReader(new CharArrayReader(str.toCharArray()));
        try {
            lnr.skip(Long.MAX_VALUE);
            lnr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lnr.getLineNumber() + 1;
    }
}
