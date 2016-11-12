package mainframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import character.Hero;
import weapon.*;

public class Main {
	public static void main(String[] args){
		
		//메인 화면 만들기 위한 클레스 생성
		MainFrame screen = new MainFrame();
		
	}
}



//캐릭터가 벽을 못넘어야 하며 공중 발판을 따로 제작해야할듯 하다, 또한 적군도 벽 밖으로 떨어지면 제거