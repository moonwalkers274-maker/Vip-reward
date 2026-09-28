package com.deepanshu.invitedemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout root, content;
    ArrayList<String> history = new ArrayList<>();
    int primary = Color.rgb(49,87,213), text = Color.rgb(24,32,51), muted = Color.rgb(110,120,145);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        showInvite();
    }

    TextView tv(String s, float size, int color) {
        TextView v = new TextView(this); v.setText(s); v.setTextSize(size); v.setTextColor(color);
        v.setFontFeatureSettings("kern"); return v;
    }
    GradientDrawable bg(int color, float r) {
        GradientDrawable g = new GradientDrawable(); g.setColor(color); g.setCornerRadius(r); return g;
    }
    Button btn(String s) {
        Button b = new Button(this); b.setText(s); b.setTextSize(15); b.setTextColor(Color.WHITE);
        b.setAllCaps(false); b.setBackground(bg(primary, 28)); b.setPadding(18,8,18,8); return b;
    }
    LinearLayout base() {
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(246,248,252));
        root.setPadding(22,18,22,18); setContentView(root); return root;
    }
    void showInvite() {
        base();
        Space sp = new Space(this); root.addView(sp, new LinearLayout.LayoutParams(1,0,0.22f));
        TextView logo = tv("DEEPANSU", 25, primary); logo.setGravity(Gravity.CENTER); logo.setTypeface(null,1);
        root.addView(logo, new LinearLayout.LayoutParams(-1,55));
        TextView sub = tv("INVITE REWARDS", 13, muted); sub.setGravity(Gravity.CENTER);
        root.addView(sub, new LinearLayout.LayoutParams(-1,35));
        LinearLayout card = new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER_HORIZONTAL); card.setPadding(24,30,24,28); card.setBackground(bg(Color.WHITE,32));
        TextView welcome = tv("Deepanshu invited you\nto join", 26, text); welcome.setGravity(Gravity.CENTER); welcome.setTypeface(null,1);
        card.addView(welcome, new LinearLayout.LayoutParams(-1,85));
        TextView info = tv("Join the invite rewards demo and explore your available reward balance.",16,muted);
        info.setGravity(Gravity.CENTER); card.addView(info, new LinearLayout.LayoutParams(-1,75));
        Button go = btn("Continue");
        LinearLayout.LayoutParams gp = new LinearLayout.LayoutParams(-1,58); gp.topMargin=18; card.addView(go,gp);
        TextView demo = tv("Invite rewards",12,muted); demo.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dp=new LinearLayout.LayoutParams(-1,40); dp.topMargin=8; card.addView(demo,dp);
        root.addView(card,new LinearLayout.LayoutParams(-1,-2));
        go.setOnClickListener(v -> showDashboard());
        Space sp2=new Space(this); root.addView(sp2,new LinearLayout.LayoutParams(1,0,0.35f));
        TextView foot=tv("Invite • Reward • Redeem",12,muted); foot.setGravity(Gravity.CENTER); root.addView(foot,new LinearLayout.LayoutParams(-1,30));
    }
    void showDashboard() {
        base();
        LinearLayout bar=new LinearLayout(this); bar.setGravity(Gravity.CENTER_VERTICAL);
        TextView title=tv("Invite Rewards",25,text); title.setTypeface(null,1); bar.addView(title,new LinearLayout.LayoutParams(0,58,1));
        TextView tag=tv("REWARDS",12,primary); tag.setGravity(Gravity.CENTER); tag.setBackground(bg(Color.rgb(231,237,255),30));
        bar.addView(tag,new LinearLayout.LayoutParams(65,36)); root.addView(bar);
        content=new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL); root.addView(content,new LinearLayout.LayoutParams(-1,0,1));
        TextView hello=tv("Hello, Deepanshu 👋",16,muted); content.addView(hello,new LinearLayout.LayoutParams(-1,40));
        LinearLayout balance=new LinearLayout(this); balance.setOrientation(LinearLayout.VERTICAL); balance.setPadding(22,18,22,18); balance.setBackground(bg(primary,28));
        TextView bt=tv("Available rewards",14,Color.WHITE); balance.addView(bt);
        TextView amount=tv("$1,000",38,Color.WHITE); amount.setTypeface(null,1); balance.addView(amount);
        TextView note=tv("Choose an amount to continue",13,Color.rgb(220,228,255)); balance.addView(note);
        LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(-1,130); bp.bottomMargin=18; content.addView(balance,bp);
        TextView choose=tv("Choose reward amount",19,text); choose.setTypeface(null,1); content.addView(choose,new LinearLayout.LayoutParams(-1,40));
        GridLayout grid=new GridLayout(this); grid.setColumnCount(2); grid.setUseDefaultMargins(true);
        int[] vals={5,10,20,50,100,200,500,1000};
        for(int x:vals){ Button b=btn("$"+x); b.setTextColor(text); b.setBackground(bg(Color.WHITE,22)); GridLayout.LayoutParams p=new GridLayout.LayoutParams(); p.width=0;p.height=58;p.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);p.setMargins(4,4,4,4);grid.addView(b,p); b.setOnClickListener(v->redeem(x)); }
        content.addView(grid,new LinearLayout.LayoutParams(-1,-2));
        TextView h=tv("Redemption history",19,text); h.setTypeface(null,1); LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,45);hp.topMargin=15;content.addView(h,hp);
        renderHistory();
    }
    void renderHistory(){
        if(content==null)return;
        TextView old=content.findViewWithTag("HISTORY"); if(old!=null) content.removeView(old);
        TextView list=tv(history.isEmpty() ? "No redemptions yet." : String.join("\n\n",history),14,muted);
        list.setTag("HISTORY"); list.setPadding(18,12,18,12); list.setBackground(bg(Color.WHITE,20));
        content.addView(list,new LinearLayout.LayoutParams(-1,-2));
    }
    void redeem(int amount){
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Redeem $"+amount+"?")
            .setMessage("This is a demo redemption. No real payment will be sent.")
            .setNegativeButton("Cancel",null)
            .setPositiveButton("Redeem", (d,w)->{
                String now=new SimpleDateFormat("dd MMM yyyy, hh:mm a",Locale.getDefault()).format(new Date());
                history.add(0,"•  $"+amount+" • Request recorded\n   "+now);
                showSuccess(amount);
            }).show();
    }
    void showSuccess(int amount){
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Request recorded")
            .setMessage("$"+amount+" redemption request has been recorded in your history. No payment is sent until a real payment provider is connected.")
            .setPositiveButton("Done",(d,w)->showDashboard()).show();
    }
}