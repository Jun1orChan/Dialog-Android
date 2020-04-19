# Dialog-Android

- 继承自DialogFragment。
- 生命周期自动管理，不用在Activity关闭的时候，进行手动dismiss。
- 支持屏幕旋转状态保留。
- 多个内置常用Dialog样式。
- 支持自定义扩展。

## 引用

1.在根目录build.gradle文件中添加

```gradle
     
 ```

2.项目build.gradle文件中添加

```gradle
 
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

## 版本记录

### 1.1.0

- 新增WebLinkDialog

### 1.0.11

- 适配Android 9
- 升级Support库到28

### 1.0.8

- 修复设置OnDismissListener可能导致的内存泄漏

### 1.0.7

- 修复MaterialDialog设置按钮颜色、文字错误的问题。

### 1.0.6

- 修复ProgressBarDialog设置cancelText错误的问题。

### 1.0.5

- 修改ProgressBarDialog样式。
- BaseDialogFragment中新增setOnDismissListener方法。
- 所有Dialog中传入的字符串类型，均修改为：CharSequence
- 修改MaterialDialog中设置btnText和单击事件的顺序，修改为：从左到右


### 1.0.2

- 修复重复调用show方法导致的Crash
- 新增isShowing()方法

### 1.0.1

- 修复旋转屏幕带来的状态销毁问题

### 1.0.0

- MaterialDialog 仿MD风格的消息弹框
- LoadingDialog 加载框
- ListDialog 列表单选框
- ProgressBarDialog 百分比进度显示
- DatePickerDialog 时间选择弹框