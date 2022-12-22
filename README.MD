# Dialog-Android

- 继承自DialogFragment。
- 生命周期自动管理，不用在Activity关闭的时候，进行手动dismiss。
- 支持屏幕旋转状态保留。
- 多个内置常用Dialog样式。
- 支持自定义扩展。
- 修复 FragmentTransaction.commit() 可能导致的问题；
- 增加 FragmentTransaction.commitAllowingStateLoss() 保护，防止crash；

## 引用


1.项目build.gradle文件中添加

```gradle
        implementation 'com.github.Jun1orChan:Dialog-Android:1.3.3'
 ```

## 使用

1.以MaterialDialog为例

```java
            final MaterialDialog materialDialog = new MaterialDialog();
    //        materialDialog.setCancelableOutside(false);
    //        materialDialog.setCancelable(false);
            materialDialog
                    .title("标题")
    //                .titleColor(Color.BLUE)
    //                .titleSize(17)
                    .content("这是内容")
    //                .content("这是一个很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
    //                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很" +
    //                        "长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
    //                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的文本")
    //                .contentColor(Color.BLACK)
    //                .contentSize(16)
                    .btnText("确定", "取消")
                    .btnColor(-1, Color.RED)
                    .btnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "确定Click", Toast.LENGTH_SHORT).show();
                            materialDialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "取消Click", Toast.LENGTH_SHORT).show();
                            materialDialog.dismiss();
                        }
                    })
                    .show(getSupportFragmentManager());
 ```
2.自定义弹框

- 继承：BaseDialogFragment（中央弹框）或者BaseBottomDialogFragment（底部弹框）
- 实现 getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

3.关于页面回收时候的处理说明
> 由于当Dialog设置了单击事件的时候，此时无法内部保存和恢复，所以由外部在界面初始化的时候，再设置一次。

1. 保存当前DialogFragment中的tag（getFragmentTag()获得），在onSaveInstanceState中保存;
2. 再使用findFragmentByTag()获取Fragment;
3. 配合isAdded()（）决定是否再次显示。

## 版本记录

### 1.3.3
- 修改Dialog默认主题；

### 1.3.2
- 修复可能导致的 FragmentTransaction crash异常保护；

### 1.3.1
- 修复可能导致的 FragmentTransaction.commit() 异常；

### 1.3.0
- 修复当页面资源回收时的异常表现问题。

### 1.2.0

- 修复可能出现的crash
- 适配Android 12
- 依赖库改为编译期间依赖

### 1.1.2
- 修复时间选择框的bug

### 1.1.1
- 文案默认中文

### 1.1.0

- 支持国际化（英文（默认），中文简体，中文繁体-台湾，中文繁体-香港）
- 修改DatePickerDialog样式和新增API

### 1.0.2
- 支持置灰

### 1.0.1
- bug fix

### 1.0.0

- MaterialDialog 仿MD风格的消息弹框
- LoadingDialog 加载框
- ListDialog 列表单选框
- ProgressBarDialog 百分比进度显示
- DatePickerDialog 时间选择弹框
- WebLinkDialog 显示html