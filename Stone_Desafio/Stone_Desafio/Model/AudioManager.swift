//
//  AudioManager.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 21/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import AVFoundation

// Singleton to control Audio throughout the application

let audioManager = AudioManager()

class AudioManager{
    
    class var sharedInstance: AudioManager{
        return audioManager
    }
    
    var musicPlayer = AVAudioPlayer()
    var player: AVAudioPlayer?
    func playBackgroundMusic(){
        
        let backgroundMusic = "StarWarsMainTheme.mp3"
        
        do{
            try musicPlayer = AVAudioPlayer(contentsOf: URL(fileURLWithPath: Bundle.main.path(forResource: backgroundMusic, ofType: nil)!))
            
        } catch{}
        musicPlayer.prepareToPlay()
        musicPlayer.numberOfLoops = -1
        musicPlayer.play()
    }
    
    func stopBackgroundMusic() {
        if self.musicPlayer.isPlaying {
            self.musicPlayer.stop()
        }
    }
    
    func playCartSound() {
        let url = Bundle.main.url(forResource: "lightsaber", withExtension: "wav")!
        
        do {
            player = try AVAudioPlayer(contentsOf: url)
            guard let player = player else { return }
            player.prepareToPlay()
            player.play()
        } catch let error as NSError {
            print(error.description)
        }
    }
}

