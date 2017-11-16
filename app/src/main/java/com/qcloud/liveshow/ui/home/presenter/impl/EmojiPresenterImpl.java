package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.ui.home.presenter.IEmojiPresenter;
import com.qcloud.liveshow.ui.home.view.IEmojiView;
import com.qcloud.qclib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：表情符号
 * Author: Kuzan
 * Date: 2017/11/16 10:00.
 */
public class EmojiPresenterImpl extends BasePresenter<IEmojiView> implements IEmojiPresenter {

    public EmojiPresenterImpl() {

    }

    @Override
    public void getEmoji(int type) {
        switch (type) {
            case 0:
                mView.replaceList(initSmile());
                break;
            default:
                mView.replaceList(initSmile());
                break;
        }
    }

    private void initClock() {

    }

    private List<String> initSmile() {
        String test = "     \uD83D\uDE25 \uD83D\uDE30 \uD83D\uDE05 \uD83D\uDE13 \uD83D\uDE29 \uD83D\uDE2B \uD83D\uDE28 \uD83D\uDE31 \uD83D\uDE20 \uD83D\uDE21 \uD83D\uDE24 \uD83D\uDE16 \uD83D\uDE06 \uD83D\uDE0B \uD83D\uDE37 \uD83D\uDE0E \uD83D\uDE34 \uD83D\uDE35 \uD83D\uDE32 \uD83D\uDE1F \uD83D\uDE26 \uD83D\uDE27 \uD83D\uDE08 \uD83D\uDC7F \uD83D\uDE2E \uD83D\uDE2C \uD83D\uDE10 \uD83D\uDE15 \uD83D\uDE2F \uD83D\uDE36 \uD83D\uDE07 \uD83D\uDE0F \uD83D\uDE11 \uD83D\uDC72 \uD83D\uDC73 \uD83D\uDC6E \uD83D\uDC77 \uD83D\uDC82 \uD83D\uDC76 \uD83D\uDC66 \uD83D\uDC67 \uD83D\uDC68 \uD83D\uDC69 \uD83D\uDC74 \uD83D\uDC75 \uD83D\uDC71 \uD83D\uDC7C \uD83D\uDC78 \uD83D\uDE3A \uD83D\uDE38 \uD83D\uDE3B \uD83D\uDE3D \uD83D\uDE3C \uD83D\uDE40 \uD83D\uDE3F \uD83D\uDE39 \uD83D\uDE3E \uD83D\uDC79 \uD83D\uDC7A \uD83D\uDE48 \uD83D\uDE49 \uD83D\uDE4A \uD83D\uDC80 \uD83D\uDC7D \uD83D\uDCA9 \uD83D\uDD25 ✨ \uD83C\uDF1F \uD83D\uDCAB \uD83D\uDCA5 \uD83D\uDCA2 \uD83D\uDCA6 \uD83D\uDCA7 \uD83D\uDCA4 \uD83D\uDCA8 \uD83D\uDC42 \uD83D\uDC40 \uD83D\uDC43 \uD83D\uDC45 \uD83D\uDC44 \uD83D\uDC4D \uD83D\uDC4E \uD83D\uDC4C \uD83D\uDC4A ✊ ✌ \uD83D\uDC4B ✋ \uD83D\uDC50 \uD83D\uDC46 \uD83D\uDC47 \uD83D\uDC49 \uD83D\uDC48 \uD83D\uDE4C \uD83D\uDE4F ☝ \uD83D\uDC4F \uD83D\uDCAA \uD83D\uDEB6 \uD83C\uDFC3 \uD83D\uDC83 \uD83D\uDC6B \uD83D\uDC6A \uD83D\uDC6C \uD83D\uDC6D \uD83D\uDC8F \uD83D\uDC91 \uD83D\uDC6F \uD83D\uDE46 \uD83D\uDE45 \uD83D\uDC81 \uD83D\uDE4B \uD83D\uDC86 \uD83D\uDC87 \uD83D\uDC85 \uD83D\uDC70 \uD83D\uDE4E \uD83D\uDE4D \uD83D\uDE47 \uD83C\uDFA9 \uD83D\uDC51 \uD83D\uDC52 \uD83D\uDC5F \uD83D\uDC5E \uD83D\uDC61 \uD83D\uDC60 \uD83D\uDC62 \uD83D\uDC55 \uD83D\uDC54 \uD83D\uDC5A \uD83D\uDC57 \uD83C\uDFBD \uD83D\uDC56 \uD83D\uDC58 \uD83D\uDC59 \uD83D\uDCBC \uD83D\uDC5C \uD83D\uDC5D \uD83D\uDC5B \uD83D\uDC53 \uD83C\uDF80 \uD83C\uDF02 \uD83D\uDC84 \uD83D\uDC9B \uD83D\uDC99 \uD83D\uDC9C \uD83D\uDC9A ❤ \uD83D\uDC94 \uD83D\uDC97 \uD83D\uDC93 \uD83D\uDC95 \uD83D\uDC96 \uD83D\uDC9E \uD83D\uDC98 \uD83D\uDC8C \uD83D\uDC8B \uD83D\uDC8D \uD83D\uDC8E \uD83D\uDC64 \uD83D\uDC65 \uD83D\uDCAC \uD83D\uDC63 \uD83D\uDCAD";
        List<String> list = new ArrayList<>();
        list.add("\uD83D\uDE04");
        list.add("\uD83D\uDE03");
        list.add("\uD83D\uDE00");
        list.add("\uD83D\uDE0A");
        list.add("\uD83D\uDE09");
        list.add("\uD83D\uDE0D");
        list.add("\uD83D\uDE18");
        list.add("\uD83D\uDE1A");
        list.add("\uD83D\uDE17");
        list.add("\uD83D\uDE19");
        list.add("\uD83D\uDE1C");
        list.add("\uD83D\uDE1D");
        list.add("\uD83D\uDE1B");
        list.add("\uD83D\uDE33");
        list.add("\uD83D\uDE01");
        list.add("\uD83D\uDE14");
        list.add("\uD83D\uDE0C");
        list.add("\uD83D\uDE12");
        list.add("\uD83D\uDE1E");
        list.add("\uD83D\uDE23");
        list.add("\uD83D\uDE22");
        list.add("\uD83D\uDE02");
        list.add("\uD83D\uDE2D");
        list.add("\uD83D\uDE2A");

        return list;
    }
}
