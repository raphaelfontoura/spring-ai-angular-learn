import { Component, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbar } from "@angular/material/toolbar";
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-simple-chat',
  imports: [MatCardModule, MatToolbar, MatInputModule, MatButtonModule, MatIconModule, FormsModule, NgClass],
  templateUrl: './simple-chat.html',
  styleUrl: './simple-chat.scss'
})
export class SimpleChat {

  userInput = '';

  messages = signal([
    { text: 'Hello, how can I help you?', isBot: true }
  ])

  sendMessage() {
    this.trimUserMessage();
    if (this.userInput !== '') {
      this.updateMessage(this.userInput);
      this.userInput = '';
      this.simulateBotResponse();
    }
  }

  private updateMessage(text: string, isBot = false) {
    this.messages.update(messages => [...messages, { text, isBot }]);
  }

  private trimUserMessage() {
    this.userInput = this.userInput.trim();
  }

  private simulateBotResponse() {
    setTimeout(() => {
      this.updateMessage('This is a simulated bot response.', true);
    }, 2000);
  }

}
