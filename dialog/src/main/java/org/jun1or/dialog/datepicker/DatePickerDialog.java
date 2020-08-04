package org.jun1or.dialog.datepicker;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;


import org.jun1or.dialog.R;
import org.jun1or.dialog.base.BaseBottomDialogFragment;
import org.jun1or.dialog.listener.TimeResultHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cwj
 */
public class DatePickerDialog extends BaseBottomDialogFragment {


    public static final int MODE_YEAR_MONTH_DAY = 2;
    public static final int MODE_YEAR_MONTH_DAY_HOUR = 3;
    public static final int MODE_YEAR_MONTH_DAY_HOUR_MINUTE = 4;
    public static final int MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = 5;

    private static final int MAX_MINUTE = 59;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MIN_HOUR = 0;
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


    public DatePickerDialog resultHandler(TimeResultHandler resultHandler) {
        mResultHandler = resultHandler;
        return this;
    }

    public DatePickerDialog startDate(Date startDate) {
        mStartCalendar.setTime(startDate);
        return this;
    }


    public DatePickerDialog endDate(Date endDate) {
        mEndCalendar.setTime(endDate);
        return this;
    }

    public DatePickerDialog selectedDate(Date selectedDate) {
        mSelectedCalender.setTime(selectedDate);
        return this;
    }

    public DatePickerDialog isLoop(boolean isLoop) {
        this.mIsLoop = isLoop;
        return this;
    }

    public DatePickerDialog mode(int mode) {
        if (mode > MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) {
            this.mMode = MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
        } else if (mode < MODE_YEAR_MONTH_DAY) {
            this.mMode = MODE_YEAR_MONTH_DAY;
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
        if (this.mMode == MODE_YEAR_MONTH_DAY) {
            mPvHour.setVisibility(View.GONE);
            view.findViewById(R.id.tvHourText).setVisibility(View.GONE);
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH_DAY_HOUR) {
            mPvMinute.setVisibility(View.GONE);
            view.findViewById(R.id.tvMinuteText).setVisibility(View.GONE);
            mPvSecond.setVisibility(View.GONE);
            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
        } else if (this.mMode == MODE_YEAR_MONTH_DAY_HOUR_MINUTE) {
            mPvSecond.setVisibility(View.GONE);
            view.findViewById(R.id.tvSecondText).setVisibility(View.GONE);
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
        loadComponent();
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
        mPvYear.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();
            }
        });

        mPvMonth.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.DAY_OF_MONTH, 1);
                mSelectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();
            }
        });

        mPvDay.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
                hourChange();
            }
        });

        mPvHour.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                minuteChange();
            }
        });

        mPvMinute.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));
            }
        });
    }

    private void monthChange() {
        mMonth.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAX_MONTH; i++) {
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

        mPvMonth.postDelayed(new Runnable() {
            @Override
            public void run() {
                dayChange();
            }
        }, 100);
    }

    private void dayChange() {
        mDay.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= mSelectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
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
        mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mDay.get(0)));
        mPvDay.setData(mDay);
        mPvDay.setSelected(0);
        executeAnimator(mPvDay);

        mPvDay.postDelayed(new Runnable() {
            @Override
            public void run() {
                hourChange();
            }
        }, 100);
    }

    private void hourChange() {
        mHour.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
            for (int i = startHour; i <= MAX_HOUR; i++) {
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
        mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mHour.get(0)));
        mPvHour.setData(mHour);
        mPvHour.setSelected(0);
        executeAnimator(mPvHour);
        mPvHour.postDelayed(new Runnable() {
            @Override
            public void run() {
                minuteChange();
            }
        }, 100);
    }

    private void minuteChange() {
        mMinute.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
            for (int i = startMinute; i <= MAX_MINUTE; i++) {
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
        mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(mMinute.get(0)));
        mPvMinute.setData(mMinute);
        mPvMinute.setSelected(0);
        executeAnimator(mPvMinute);
        mPvHour.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondChange();
            }
        }, 100);
    }

    private void secondChange() {
        mSecond.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
        int selectedHour = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
        int selectedMinute = mSelectedCalender.get(Calendar.MINUTE);
        if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour && selectedMinute == startMinute) {
            for (int i = startSec; i <= MAX_MINUTE; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
            for (int i = MIN_MINUTE; i <= endSec; i++) {
                mSecond.add(formatTimeUnit(i));
            }
        } else {
            for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatStr = simpleDateFormat.format(time);
        String[] str = formatStr.split(" ");
        String[] dateStr = str[0].split("-");
        mPvYear.setSelected(dateStr[0]);
        mSelectedCalender.set(Calendar.YEAR, Integer.parseInt(dateStr[0]));
        mMonth.clear();
        int selectedYear = mSelectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAX_MONTH; i++) {
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
        mPvMonth.setData(mMonth);
        mPvMonth.setSelected(dateStr[1]);
        mSelectedCalender.set(Calendar.MONTH, Integer.parseInt(dateStr[1]) - 1);
        executeAnimator(mPvMonth);
        mDay.clear();
        int selectedMonth = mSelectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= mSelectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
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
        mPvDay.setSelected(dateStr[2]);
        mSelectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr[2]));
        executeAnimator(mPvDay);

        if (str.length == 2) {
            String[] timeStr = str[1].split(":");
            mHour.clear();
            int selectedDay = mSelectedCalender.get(Calendar.DAY_OF_MONTH);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                for (int i = startHour; i <= MAX_HOUR; i++) {
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
            mPvHour.setSelected(timeStr[0]);
            mSelectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr[0]));
            executeAnimator(mPvHour);
            mMinute.clear();
            int selectedHour = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                for (int i = startMinute; i <= MAX_MINUTE; i++) {
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
            mPvMinute.setSelected(timeStr[1]);
            mSelectedCalender.set(Calendar.MINUTE, Integer.parseInt(timeStr[1]));
            executeAnimator(mPvMinute);

            mSecond.clear();
            int selectedMinute = mSelectedCalender.get(Calendar.HOUR_OF_DAY);
            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour && selectedMinute == startMinute) {
                for (int i = startSec; i <= MAX_MINUTE; i++) {
                    mSecond.add(formatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                for (int i = MIN_MINUTE; i <= endMinute; i++) {
                    mSecond.add(formatTimeUnit(i));
                }
            } else {
                for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
                    mSecond.add(formatTimeUnit(i));
                }
            }
            mPvSecond.setData(mSecond);
            mPvSecond.setSelected(timeStr[1]);
            mSelectedCalender.set(Calendar.SECOND, Integer.parseInt(timeStr[1]));
            executeAnimator(mPvSecond);
        }
        executeScroll();
    }
}
