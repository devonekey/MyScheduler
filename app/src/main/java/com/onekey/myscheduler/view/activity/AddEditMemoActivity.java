package com.onekey.myscheduler.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.onekey.myscheduler.R;
import com.onekey.myscheduler.contract.AddEditMemoContract;
import com.onekey.myscheduler.etc.ConstantsEntry;
import com.onekey.myscheduler.presenter.AddEditMemoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class AddEditMemoActivity
        extends AbstractActivity<AddEditMemoContract.Presenter>
        implements AddEditMemoContract.Activity<AddEditMemoContract.Presenter>, ConstantsEntry {
    @BindView(R.id.activity_add_edit_memo_title_edit_text)
    EditText titleEditText;
    @BindView(R.id.activity_add_edit_memo_description_edit_text)
    EditText descriptionEditText;

    private int position;

    public static final int ADD_MEMO_TASK_REQUEST_CODE = 1000;
    public static final int EDIT_MEMO_TASK_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setView(R.layout.activity_add_edit_memo);

        Intent intent = getIntent();
        position = intent.getIntExtra(CLICKED_ITEM_POSITION, DEFAULT_POSITION);

        presenter = AddEditMemoPresenter.getInstance(getApplicationContext());
        presenter.setActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setPosition(position);
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.end();
    }

    @Override
    public void setTitle(String title) {
        titleEditText.setText(title);
    }

    @Override
    public void setDescription(String description) {
        descriptionEditText.setText(description);
    }

    @Override
    public void successfullySaved() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.activity_add_edit_memo_save_button)
    void onClick(View view) {
        presenter.save(titleEditText.getText().toString(), descriptionEditText.getText().toString());
    }
}
