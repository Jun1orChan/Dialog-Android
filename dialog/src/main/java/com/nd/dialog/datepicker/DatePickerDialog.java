package com.nd.dialog.datepicker;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.nd.dialog.R;
import com.nd.dialog.base.BaseBottomDialogFragment;


import com.nd.dialog.listener.TimeResultHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cwj
 */
public class DatePickerDialog extends BaseBottomDialogFragment {

    /**
     * 选择模式：年
     */
    public static final int MODE_YEAR = 0x001;
    /**
     * 选择模式：年-月
     */
    public static final int MODE_YEAR_MONTH = 0x002;
    /**
     * 选择模式：年-月-日
     */
    public static final int MODE_YEAR_MONTH_DAY = 0x003;
    /**
     * 选择模式：年-月-日 时
     */
    public static final int MODE_YEAR_MONTH_DAY_HOUR = 0x004;
    /**
     * 选择模式：年-月-日 时:分
     */
    public static final int MODE_YEAR_MONTH_DAY_HOUR_MINUTE = 0x005;
    /**
     * 选择模式：年-月-日 时:分:秒
     */
    public static final int MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = 0x006;

    /**
     * 秒_最大值
     */
    private static final int MAX_SECOND = 59;
    /**
     * 秒_最小值
     */
    private static final int MIN_SECOND = 0;
    /**
     * 分钟_最大值
     */
    private static final int MAX_MINUTE = 59;
    /**
     * 分钟_最小值
     */
    private static final int MIN_MINUTE = 0;
    /**
     * 小时_最大值
     */
    private static final int MAX_HOUR = 23;
    /**
     * 小时_最小值
     */
    private static final int MIN_HOUR = 0;
    /**
     * 月份_最大值
     */
    private static final int MAX_MONTH = 12;

    private TimeResultHandler mResultHandler;
    private DatePickerView mPvYear, mPvMonth, mPvDay, mPvHour, mPvMinute, mPvSecond;
    private ArrayList<String> mYear, mMonth, mDay, mHour, mMinute, mSecond;
    private int startYear, startMonth, startDay, startHour, startMinute, startSec, endYear, endMonth, endDay, endHour, endMinute, endSec;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin, spanSec;
    private Calendar mSelectedCalender = Calendar.getInstance(),
            mStartCalendar = Calendar.getInstance(),
            mEndCalendar = Calendar.getInstance();
    private TextView mTvCancel, mTvSelect;
    private boolean mIsLoop;
    private int mMode = MODE_YEAR_MONTH_DAY_HOUR_MINUTE;

    private CharSequence mCancelText, mConfirmText;

    private int mCancelTextColor = -1, mConfirmTextColor = -1, mSeparatorColor = -1;


    /**
     * 设置结果回调
     *
     * @param resultHandler
     * @return
     */
    public DatePickerDialog resultHandler(TimeResultHandler resultHandler) {
        mResultHandler = resultHandler;
        return this;
    }

    /**
     * 设置开始时间
     *
     * @param startDate 开始时间
     * @return
     */
    public DatePickerDialog startDate(Date startDate) {
        mStartCalendar.setTime(startDate);
        return this;
    }

    /**
     * 设置结束时间
     *
     * @param endDate 结束时间
     * @return
     */
    public DatePickerDialog endDate(Date endDate) {
        mEndCalendar.setTime(endDate);
        return this;
    }


    /**
     * 设置当前选中的时间
     *
     * @param selectedDate 当前选中时间
     * @return
     */
    public DatePickerDialog selectedDate(Date selectedDate) {
        mSelectedCalender.setTime(selectedDate);
        return this;
    }

    /**
     * 是否上下循环
     *
     * @param isLoop 是否循环
     * @return
     */
    public DatePickerDialog isLoop(boolean isLoop) {
        this.mIsLoop = isLoop;
        return this;
    }

    /**
     * 设置取消文本
     *
     * @param cancelText 取消文本
     * @return
     */
    public DatePickerDialog cancelText(CharSequence cancelText) {
        this.mCancelText = cancelText;
        return this;
    }

    /**
     * 设置确认文本
     *
     * @param confirmText 确认文本
     * @return
     */
    public DatePickerDialog confirmText(CharSequence confirmText) {
        this.mConfirmText = confirmText;
        return this;
    }

    /**
     * 设置取消文本颜色
     *
     * @param cancelTextColor 颜色
     * @return
     */
    public DatePickerDialog cancelColor(@ColorInt int cancelTextColor) {
        this.mCancelTextColor = cancelTextColor;
        return this;
    }

    /**
     * 设置确认文本颜色
     *
     * @param confirmTextColor 颜色
     * @return
     */
    public DatePickerDialog confirmColor(@ColorInt int confirmTextColor) {
        this.mConfirmTextColor = confirmTextColor;
        return this;
    }

    /**
     * 设置分隔符文本颜色
     *
     * @param separatorColor 颜色
     * @return
     */
    public DatePickerDialog separatorColor(@ColorInt int separatorColor) {
        this.mSeparatorColor = separatorColor;
        return this;
    }

    /**
     * 设置当前模式
     * 目前支持：年、年-月、年-月-日、年-月-日 时、年-月-日 时:分、年-月-日 时:分:秒
     *
     * @param mode
     * @return
     */
    public DatePickerDialog mode(int mode) {
        if (mode > MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) {
            this.mMode = MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
        } else if (mode < MODE_YEAR) {
            this.mMode = MODE_YEAR;
        } else {
            this.mMode = mode;
        }
        return this;
    }

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialoglib_datepicker, null, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        setIsLoop(mIsLoop);
        initParameter();
        initTimer();
        setSelectedTime(mSelectedCalender.getTime());
    }


    private void initView(View view) {
        mPvYear = (DatePickerView) view.findViewById(R.id.pvYear);
        if (mSeparatorColor != -1) {
            ((TextView) view.findViewById(R.id.tvYearText)).setTextColor(mSeparatorColor);
            ((TextView) view.findViewById(R.id.tvMonthText)).setTextColor(mSeparatorColor);
            ((TextView) view.findViewById(R.id.tvDayText)).setTextColor(mSeparatorColor);
            ((TextView) view.findViewById(R.id.tvHourText)).setTextColor(mSeparatorColor);
            ((TextView) view.findViewById(R.id.tvMinuteText)).setTextColor(mSeparatorColor);
        }
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvSelect);
        if (mCancelTextColor != -1) {
            tvCancel.setTextColor(mCancelTextColor);
        }
        if (!TextUtils.isEmpty(mCancelText)) {
            tvCancel.setText(mCancelText);
        }
        if (mConfirmTextColor != -1) {
            tvConfirm.setTextColor(mConfirmTextColor);
        }
        if (!TextUtils.isEmpty(mConfirmText)) {
            tvConfirm.setText(mConfirmText);
        }
        mPvMonth = (DatePickerView) view.findViewById(R.id.pvMonth);
        mPvDay = (DatePickerView) view.findViewById(R.id.pvDay);
        mPvHour = (DatePickerView) view.findViewById(R.id.pvHour);
        mPvMinute = (DatePickerView) view.findViewById(R.id.pvMinute);
        mPvSecond = (DatePickerView) view.findViewById(R.id.pvSecond);
        mTvCancel = (TextView) view.findViewById(R.id.tvCancel);
        mTvSelect = (TextView) view.findViewById(R.id.tvSelect);

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mTvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultHandler.onTimeHandle(mSelectedCalender.getTime());
                dismiss();
            }
        });
        addListener();
        initMode(view);
    }

    private void initMode(View view) {
        if (this.mMode == MODE_YEAR) {
            view.findViewById(R.id.tvYearText).setVisibility(View.GONE);
            mPvMonth.setVisibility(View.GONE);
            view.findViewById(R.id.tvMonthText).setVisibility(View.GONE);
            mPvDay.setVisibility(View.GONE);
            view.findViewById(R.id.tvDayText).setVisibility(View.GONE);
            mPvHour.setVisibility(View.GONE);
            view.findViewById(R.id.tvHourText).setVisibility(View.GONE);
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH) {
            view.findViewById(R.id.tvMonthText).setVisibility(View.GONE);
            mPvDay.setVisibility(View.GONE);
            view.findViewById(R.id.tvDayText).setVisibility(View.GONE);
            mPvHour.setVisibility(View.GONE);
            view.findViewById(R.id.tvHourText).setVisibility(View.GONE);
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH_DAY) {
            mPvHour.setVisibility(View.GONE);
            view.findViewById(R.id.tvHourText).setVisibility(View.GONE);
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
//            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH_DAY_HOUR) {
            view.findViewById(R.id.tvHourText).setVisibility(View.GONE);
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
//            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH_DAY_HOUR_MINUTE) {
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
//            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
        }
    }

    private void initParameter() {
        startYear = mStartCalendar.get(Calendar.YEAR);
        startMonth = mStartCalendar.get(Calendar.MONTH) + 1;
        startDay = mStartCalendar.get(Calendar.DAY_OF_MONTH);
        startHour = mStartCalendar.get(Calendar.HOUR_OF_DAY);
        startMinute = mStartCalendar.get(Calendar.MINUTE);
        startSec = mStartCalendar.get(Calendar.SECOND);
        endYear = mEndCalendar.get(Calendar.YEAR);
        endMonth = mEndCalendar.get(Calendar.MONTH) + 1;
        endDay = mEndCalendar.get(Calendar.DAY_OF_MONTH);
        endHour = mEndCalendar.get(Calendar.HOUR_OF_DAY);
        endMinute = mEndCalendar.get(Calendar.MINUTE);
        endSec = mEndCalendar.get(Calendar.SECOND);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        spanHour = (!spanDay) && (startHour != endHour);
        spanMin = (!spanHour) && (startMinute != endMinute);
        spanSec = (!spanSec) && (startSec != endSec);
    }

    private void initTimer() {
        initArrayList();
        initTimeList();
        loadComponent();
    }

    private void initTimeList() {
        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                mYear.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAX_MONTH; i++) {
                mMonth.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= mStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                mDay.add(formatTimeUnit(i));
            }
            for (int i = startHour; i <= MAX_HOUR; i++) {
                mHour.add(formatTimeUnit(i));
            }
            for (int i = startMinute; i <= MAX_MINUTE; i++) {
                mMinute.add(formatTimeUnit(i));
            }
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (spanMon) {
            mYear.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                mMonth.add(formatTimeUnit(i));
            }
            for (int i = startDay; i <= mStartCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                mDay.add(formatTimeUnit(i));
            }
            for (int i = startHour; i <= MAX_HOUR; i++) {
                mHour.add(formatTimeUnit(i));
            }
            for (int i = startMinute; i <= MAX_MINUTE; i++) {
                mMinute.add(formatTimeUnit(i));
            }
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (spanDay) {
            mYear.add(String.valueOf(startYear));
            mMonth.add(formatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                mDay.add(formatTimeUnit(i));
            }
            mHour.add(formatTimeUnit(startHour));
            for (int i = startHour; i <= MAX_HOUR; i++) {
                mHour.add(formatTimeUnit(i));
            }
            for (int i = startMinute; i <= MAX_MINUTE; i++) {
                mMinute.add(formatTimeUnit(i));
            }
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (spanHour) {
            mYear.add(String.valueOf(startYear));
            mMonth.add(formatTimeUnit(startMonth));
            mDay.add(formatTimeUnit(startDay));
            for (int i = startHour; i <= endHour; i++) {
                mHour.add(formatTimeUnit(i));
            }
            for (int i = startMinute; i <= MAX_MINUTE; i++) {
                mMinute.add(formatTimeUnit(i));
            }
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (spanMin) {
            mYear.add(String.valueOf(startYear));
            mMonth.add(formatTimeUnit(startMonth));
            mDay.add(formatTimeUnit(startDay));
            mHour.add(formatTimeUnit(startHour));
            for (int i = startMinute; i <= endMinute; i++) {
                mMinute.add(formatTimeUnit(i));
            }
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (spanSec) {
            mYear.add(String.valueOf(startYear));
            mMonth.add(formatTimeUnit(startMonth));
            mDay.add(formatTimeUnit(startDay));
            mHour.add(formatTimeUnit(startHour));
            mMinute.add(formatTimeUnit(startMinute));
            for (int i = startSec; i <= endSec; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        }
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (mYear == null) {
            mYear = new ArrayList<>();
        }
        if (mMonth == null) {
            mMonth = new ArrayList<>();
        }
        if (mDay == null) {
            mDay = new ArrayList<>();
        }
        if (mHour == null) {
            mHour = new ArrayList<>();
        }
        if (mMinute == null) {
            mMinute = new ArrayList<>();
        }
        if (mSecond == null) {
            mSecond = new ArrayList<>();
        }
        mYear.clear();
        mMonth.clear();
        mDay.clear();
        mHour.clear();
        mMinute.clear();
        mSecond.clear();
    }

    private void loadComponent() {
        mPvYear.setData(mYear);
        mPvMonth.setData(mMonth);
        mPvDay.setData(mDay);
        mPvHour.setData(mHour);
        mPvMinute.setData(mMinute);
        mPvSecond.setData(mSecond);
        mPvYear.setSelected(0);
        mPvMonth.setSelected(0);
        mPvDay.setSelected(0);
        mPvHour.setSelected(0);
        mPvMinute.setSelected(0);
        mPvSecond.setSelected(0);
        executeScroll();
    }

    private void addListener() {
        mPvYear.setOnSelectListener(new DatePickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange(true);
            }
        });

        mPvMonth.setOnSelectListener(new DatePickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
//                mSelectedCalender.set(Calendar.DAY_OF_MONTH, 1);
                mSelectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange(true);
            }
        });

        mPvDay.setOnSelectListener(new DatePickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
                hourChange(true);
            }
        });

        mPvHour.setOnSelectListener(new DatePickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                minuteChange(true);
            }
        });

        mPvMinute.setOnSelectListener(new DatePickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));
                secondChange(true);
            }
        });
    }

    private void monthChange(boolean nextAction) {
        mMonth.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            int maxMonth = MAX_MONTH;
            if (selectedYear == endYear) {
                maxMonth = endMonth;
            }
            for (int i = startMonth; i <= maxMonth; i++) {
                mMonth.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                mMonth.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAX_MONTH; i++) {
                mMonth.add(formatTimeUnit(i));
            }
        }
        mSelectedCalender.set(Calendar.MONTH, Integer.parseInt(mMonth.get(0)) - 1);
        mPvMonth.setData(mMonth);
        mPvMonth.setSelected(0);
        executeAnimator(mPvMonth);
        if (nextAction) {
            mPvMonth.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dayChange(true);
                }
            }, 100);
        }
        executeScroll();
    }

    private void dayChange(boolean nextAction) {
        mDay.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            int maxDay = mSelectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (selectedYear == endYear && selectedMonth == endMonth) {
                maxDay = endDay;
            }
            for (int i = startDay; i <= maxDay; i++) {
                mDay.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                mDay.add(formatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= mSelectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                mDay.add(formatTimeUnit(i));
            }
        }
        mPvDay.setData(mDay);
        executeAnimator(mPvDay);
        if (nextAction) {
            mPvDay.setSelected(0);
            mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mDay.get(0)));
            mPvDay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hourChange(true);
                }
            }, 100);
        }
        executeScroll();
    }

    private void hourChange(boolean nextAction) {
        mHour.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
            int maxHour = MAX_HOUR;
            if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                maxHour = endHour;
            }
            for (int i = startHour; i <= maxHour; i++) {
                mHour.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
            for (int i = MIN_HOUR; i <= endHour; i++) {
                mHour.add(formatTimeUnit(i));
            }
        } else {
            for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
                mHour.add(formatTimeUnit(i));
            }
        }
        mPvHour.setData(mHour);
        executeAnimator(mPvHour);
        if (nextAction) {
            mPvHour.setSelected(0);
            mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHour.get(0)));
            mPvHour.postDelayed(new Runnable() {
                @Override
                public void run() {
                    minuteChange(true);
                }
            }, 100);
        }
        executeScroll();
    }

    private void minuteChange(boolean nextAction) {
        mMinute.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
            int maxMinute = MAX_MINUTE;
            if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                maxMinute = endMinute;
            }
            for (int i = startMinute; i <= maxMinute; i++) {
                mMinute.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
            for (int i = MIN_MINUTE; i <= endMinute; i++) {
                mMinute.add(formatTimeUnit(i));
            }
        } else {
            for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                mMinute.add(formatTimeUnit(i));
            }
        }
        mPvMinute.setData(mMinute);
        executeAnimator(mPvMinute);
        if (nextAction) {
            mPvMinute.setSelected(0);
            mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(mMinute.get(0)));
            mPvHour.postDelayed(new Runnable() {
                @Override
                public void run() {
                    secondChange(true);
                }
            }, 100);
        }
        executeScroll();
    }

    private void secondChange(boolean nextAction) {
        mSecond.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
        int selectedMinute = mSelectedCalender.get(Calendar.MINUTE);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour && selectedMinute == startMinute) {
            int maxSecond = MAX_SECOND;
            if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour && selectedMinute == endMinute) {
                maxSecond = endSec;
            }
            for (int i = startSec; i <= maxSecond; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour && selectedMinute == endMinute) {
            for (int i = MIN_SECOND; i <= endSec; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else {
            for (int i = MIN_SECOND; i <= MAX_SECOND; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        }
        mSelectedCalender.set(Calendar.SECOND, Integer.parseInt(mSecond.get(0)));
        mPvSecond.setData(mSecond);
        mPvSecond.setSelected(0);
        executeAnimator(mPvSecond);
        executeScroll();
    }

    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start();
    }

    private void executeScroll() {
        mPvYear.setCanScroll(mYear.size() > 1);
        mPvMonth.setCanScroll(mMonth.size() > 1);
        mPvDay.setCanScroll(mDay.size() > 1);
        mPvHour.setCanScroll(mHour.size() > 1);
        mPvMinute.setCanScroll(mMinute.size() > 1);
        mPvSecond.setCanScroll(mSecond.size() > 1);
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setIsLoop(boolean isLoop) {
        this.mPvYear.setIsLoop(isLoop);
        this.mPvMonth.setIsLoop(isLoop);
        this.mPvDay.setIsLoop(isLoop);
        this.mPvHour.setIsLoop(isLoop);
        this.mPvMinute.setIsLoop(isLoop);
        this.mPvSecond.setIsLoop(isLoop);
    }

    /**
     * 设置日期控件默认选中的时间
     */
    public void setSelectedTime(Date time) {
        if (time.getTime() < mStartCalendar.getTime().getTime() || time.getTime() > mEndCalendar.getTime().getTime()) {
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatStr = simpleDateFormat.format(time);
        String[] str = formatStr.split(" ");
        String[] dateStr = str[0].split("-");
        String[] timeStr = str[1].split(":");
        mPvYear.setSelected(dateStr[0]);
        mSelectedCalender.set(Calendar.YEAR, Integer.parseInt(dateStr[0]));
        monthChange(false);
        mPvMonth.setSelected(dateStr[1]);
        mSelectedCalender.set(Calendar.MONTH, Integer.parseInt(dateStr[1]) - 1);
        dayChange(false);
        mPvDay.setSelected(dateStr[2]);
        mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr[2]));
        hourChange(false);
        mPvHour.setSelected(timeStr[0]);
        mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr[0]));
        minuteChange(false);
        mPvMinute.setSelected(timeStr[1]);
        mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(timeStr[2]));
        secondChange(false);
        mPvSecond.setSelected(timeStr[2]);
    }
}
