package frame.zzt.com.appframe.signed;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * 自定义签到功能
 * Created by zeting
 * Date 19/7/15.
 */

public class ActivitySigned extends BaseAppCompatActivity {

    private StepsView mStepView ;
    private Signin signin;
    private MySignedView my_sign_view;
    private List<String> signInData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed);


        mStepView = (StepsView) findViewById(R.id.step_view);
        mStepView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<StepBean> mStepBeans = new ArrayList<>();
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0715"));
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0716"));
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0717"));
                mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, -1, "0718"));
                mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 0, "0719"));
                mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 0, "0720"));
                mStepBeans.add(new StepBean(StepBean.STEP_UNDO, 0, "0721"));
                mStepView.setStepNum(mStepBeans);
            }
        } , 1000);

        mStepView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<StepBean> mStepBeans = new ArrayList<>();
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0715"));
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0716"));
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0717"));
                mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, -1, "0718"));
                mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, -1, "0719"));
                mStepBeans.add(new StepBean(StepBean.STEP_UNDO, -1, "0720"));
                mStepBeans.add(new StepBean(StepBean.STEP_UNDO, -1, "0721"));
                mStepView.setStepNum(mStepBeans);
            }
        } , 2000);

        mStepView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStepView.startSignAnimation( 3  );
            }
        } , 3000);


        signin = (Signin) findViewById(R.id.sigin);
        signInData.add("第一天");
        signInData.add("第二天");
        signInData.add("第三天");
        signInData.add("第四天");
        signInData.add("第五天");
//        signInData.add("第六天");
//        signInData.add("第七天");
        signin.setSignInData(signInData);

        signin.postDelayed(new Runnable() {
            @Override
            public void run() {
                signin.setCurretn(2);
            }
        } , 1000 );


        my_sign_view = (MySignedView) findViewById(R.id.my_sign_view);
        my_sign_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<SignBean> mSignBeens = new ArrayList<>();
                mSignBeens.add(new SignBean(StepBean.STEP_COMPLETED, -1, "0715"));
                mSignBeens.add(new SignBean(StepBean.STEP_COMPLETED, -1, "0716"));
                mSignBeens.add(new SignBean(StepBean.STEP_COMPLETED, -1, "0717"));
                mSignBeens.add(new SignBean(StepBean.STEP_CURRENT, -1, "0718"));
                mSignBeens.add(new SignBean(StepBean.STEP_UNDO, 0, "0719"));
                mSignBeens.add(new SignBean(StepBean.STEP_UNDO, 0, "0720"));
                mSignBeens.add(new SignBean(StepBean.STEP_UNDO, 0, "0721"));
                my_sign_view.setStepNum(mSignBeens);
            }
        } , 2000);



    }


}
