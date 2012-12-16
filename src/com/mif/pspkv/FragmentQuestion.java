package com.mif.pspkv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentQuestion extends SherlockFragment {

	int question_no = 0;

	public static Fragment newInstance(int question_no) {
		Fragment f = new FragmentQuestion();

		Bundle args = new Bundle();
		args.putInt("question_no", question_no);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		question_no = getArguments().getInt("question_no");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_question, container, false);
		this.initView(v, question_no);
		return v;
	}

	private void initView(View v, int question_no) {
		final Question q = ((QuestionsActivity) getSherlockActivity()).mQuestions
				.get(question_no);
		((TextView) v.findViewById(R.id.question)).setText(q.Content());
		final Button why = ((Button) v.findViewById(R.id.button_why));
		final TextView explain = ((TextView) v.findViewById(R.id.explain));
		explain.setText(q.Details());
		why.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				explain.setVisibility(View.VISIBLE);
				v.setVisibility(View.GONE);
			}
		});

		final RadioGroup answers = (RadioGroup) v.findViewById(R.id.answers);
		for (Answer a : q.Answers())
			((RadioButton) answers.getChildAt(q.Answers().indexOf(a)))
					.setText(a.Content());

		answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				((QuestionsActivity) getSherlockActivity())
						.buttonNextEnable(true);
				int rightId = rightAnswerId(q);
				boolean correct = checkedId == rightId;
				why.setVisibility(View.VISIBLE);

				for (int i = 0; i < 4; i++) {
					group.getChildAt(i).setEnabled(false);
				}
				if (correct) {
					((RadioButton) group.findViewById(checkedId))
							.setTextColor(Color.GREEN);
				} else {
					((RadioButton) group.findViewById(checkedId))
							.setTextColor(Color.RED);
					((RadioButton) group.findViewById(rightId))
							.setTextColor(Color.GREEN);
					((QuestionsActivity) getSherlockActivity())
							.setWrong(FragmentQuestion.this.question_no);
				}
			}
		});
	}

	private int rightAnswerId(Question q) {
		int no = 0;
		for (Answer a : q.Answers())
			if (a.IsRight())
				no = q.Answers().indexOf(a);
		if (no == 0)
			return R.id.radio0;
		if (no == 1)
			return R.id.radio1;
		if (no == 2)
			return R.id.radio2;
		return R.id.radio3;
	}

}