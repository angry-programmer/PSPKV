package com.mif.pspkv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class QuestionsActivity extends SherlockFragmentActivity {

	public static final int TEST_1 = 1;
	public static final int TEST_2 = 2;
	public static final int TEST_3 = 3;
	public static final int TEST_4 = 4;
	public static final int TEST_ALL = 5;

	List<Question> mQuestions = null;
	List<Fragment> mBackStack = null;
	List<Fragment> mWrong = null;
	int question_no = -1;

	@Override
	public void onBackPressed() {
		if (question_no == 0)
			finish();
		else
			super.onBackPressed();
		question_no -= 1;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);
		getSupportActionBar().hide();
		mBackStack = new ArrayList<Fragment>();
		mWrong = new ArrayList<Fragment>();
		newFragment();
		mQuestions = loadQuestions(getIntent().getExtras().getInt("test_no"));
		Collections.shuffle(mQuestions);

		((Button) findViewById(R.id.button1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						newFragment();
					}
				});

	}

	void newFragment() {
		question_no++;
		Fragment f = null;
		if (question_no < 60) {
			if (mBackStack.size() > question_no) {
				f = mBackStack.get(question_no);
			} else {
				buttonNextEnable(false);
				f = FragmentQuestion.newInstance(question_no);
			}
		} else if (mWrong.size() > 0 && mBackStack.size() >= 60) {
			buttonNextEnable(false);
			f = mWrong.get(0);
			mWrong.remove(0);
		} else {
			finish();
		}
		addToBackStack(f);
	}

	private void addToBackStack(Fragment f) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
				R.anim.fragment_slide_left_exit,
				R.anim.fragment_slide_right_enter,
				R.anim.fragment_slide_right_exit);
		ft.replace(R.id.fragment, f);
		ft.setTransition(FragmentTransaction.TRANSIT_UNSET);
		ft.addToBackStack(null);
		ft.commit();
		if (!mBackStack.contains(f))
			mBackStack.add(f);
	}

	public void setWrong(int question_no) {
		mWrong.add(FragmentQuestion.newInstance(question_no));
	}

	public void buttonNextEnable(boolean enable) {
		((Button) findViewById(R.id.button1)).setEnabled(enable);
	}

	private List<Question> loadQuestions(int test_no) {
		List<Question> _questions = new ArrayList<Question>();
		XmlResourceParser parser = null;
		switch (test_no) {
		case TEST_1:
			 parser = getResources().getXml(R.xml.test1);
			break;
		case TEST_2:
			parser = getResources().getXml(R.xml.test2);
			break;
		case TEST_3:
			 parser = getResources().getXml(R.xml.test3);
			break;
		case TEST_4:
			 parser = getResources().getXml(R.xml.test4);
			break;
		case TEST_ALL:
			_questions.addAll(loadQuestions(TEST_1));
			_questions.addAll(loadQuestions(TEST_2));
			_questions.addAll(loadQuestions(TEST_3));
			_questions.addAll(loadQuestions(TEST_4));
			break;

		default:
			break;
		}

		if (parser == null)
			return _questions;

		try {
			parser.next();
			int event;
			Question _question = null;
			List<Answer> _answers = null;
			Answer _answer = null;
			boolean tagAnswer = false;
			boolean tagContent = false;
			boolean tagExplain = false;
			while ((event = parser.next()) != XmlResourceParser.END_DOCUMENT) {
				if (event == XmlResourceParser.START_TAG) {
					if (parser.getName().equalsIgnoreCase("question")) {
						_question = new Question();
					} else if (parser.getName().equalsIgnoreCase("text")) {
						tagContent = true;
						tagAnswer = false;
						tagExplain = false;
					} else if (parser.getName().equalsIgnoreCase("answer")) {
						_answer = new Answer();
						if (parser.getAttributeCount() != -1) {
							_answer.IsRight(Boolean.parseBoolean(parser
									.getAttributeValue(0)));
						}
						tagContent = false;
						tagAnswer = true;
						tagExplain = false;
					} else if (parser.getName().equalsIgnoreCase("explain")) {
						tagContent = false;
						tagAnswer = false;
						tagExplain = true;
					} else if (parser.getName().equalsIgnoreCase("answers")) {
						_answers = new ArrayList<Answer>();
					}
				} else if (event == XmlResourceParser.TEXT) {
					if (tagAnswer) {
						_answer.Content(parser.getText());
					} else if (tagContent) {
						_question.Content(parser.getText());
					} else if (tagExplain) {
						_question.Details(parser.getText());
					}
				} else if (event == XmlResourceParser.END_TAG) {
					if (parser.getName().equalsIgnoreCase("question")) {
						_questions.add(_question);
					} else if (parser.getName().equalsIgnoreCase("answer")) {
						_answers.add(_answer);
					} else if (parser.getName().equalsIgnoreCase("answers")) {
						_question.Answers(_answers);
					}
				}
			}
		} catch (final XmlPullParserException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return _questions;
	}
}