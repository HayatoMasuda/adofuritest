//
//  ViewController.swift
//  testAdfuri
//
//  Created by 101380 on 2018/04/03.
//  Copyright © 2018年 101380. All rights reserved.
//

import UIKit

class ViewController: UIViewController, ADFmyMovieRewardDelegate {

    var movieReward: ADFmyMovieReward?
    
    @IBAction func Tap(_ sender: Any) {
        print("Tap!")
        if let movieReward = self.movieReward, movieReward.isPrepared() {
            movieReward.play()
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        // デリゲートをセットしてADFmyMovieRewardのインスタンスを取得する
        self.movieReward = ADFmyMovieReward.getInstance(MOVIE_REWARD_APPID, delegate: self)
        print( "広告読み込み中")
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // ログをUITextViewに表示(サンプルアプリ用)
//    func printLog(text: String) {
//        let formatter = DateFormatter()
//        formatter.dateFormat = "HH:mm:ss.SSSS"
////        print(self.logTextView.text + "\(formatter.string(from: Date())) [MovieReward] \(text)\n")
//    }
    
    // MARK: - ADFmyMovieRewardDelegate
    
    // 動画広告の読み込みが完了した時に呼ばれるデリゲート
    func adsFetchCompleted(_ appID: String!, isTestMode isTestMode_inApp: Bool) {
        print("読み込み完了")
    }
    
    // 動画広告の表示を開始した時に呼ばれるデリゲート
    func adsDidShow(_ appID: String!, adNetworkKey: String!) {
        if let adNetworkKey = adNetworkKey {
//            printLog(text: "閲覧開始: \(adNetworkKey)")
        }
//        if let label = self.incentiveLabel.text, let point = Int(label) {
//            self.incentiveLabel.text = String(point + 1)
//        }
    }
    
    // 動画広告の再生が完了した時に呼ばれるデリゲート
    func adsDidCompleteShow(_ appID: String!) {
        print( "再生完了")
    }
    
    // 動画広告を閉じた時に呼ばれるデリゲート
    func adsDidHide(_ appID: String!) {
        print( "動画広告を閉じました")
    }
    
    // 動画広告の再生エラー時に呼ばれるデリゲート
    func adsPlayFailed(_ appID: String!) {
        print( "動画広告再生エラー")
    }

}

