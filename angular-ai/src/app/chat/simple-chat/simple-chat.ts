import { Component, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatToolbar } from "@angular/material/toolbar";
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { ChatService } from '../chat-service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-simple-chat',
  imports: [MatCardModule, MatToolbar, MatInputModule, MatButtonModule, MatIconModule, FormsModule, NgClass],
  templateUrl: './simple-chat.html',
  styleUrl: './simple-chat.scss'
})
export class SimpleChat {

  @ViewChild('chatHistory')
  private readonly chatHistory!: ElementRef;

  private readonly chatService = inject(ChatService);

  userInput = '';

  isLoading = signal(false);

  local = false;

  messages = signal([
    { text: 'Hello, how can I help you?', isBot: true }
  ])

  sendMessage() {
    this.trimUserMessage();
    if (this.userInput !== '' && !this.isLoading()) {
      this.updateMessage(this.userInput);
      this.isLoading.set(true);
      if (this.local) {
        this.simulateBotResponse();
        return;
      }
      this.sendChatMessage();
    }
  }

  private sendChatMessage() {
    this.chatService.sendChatMessage(this.userInput)
      .subscribe({
        next: (response) => {
          this.updateMessage(response.message, true);
          this.isLoading.set(false);
          this.userInput = '';
        },
        error: () => {
          this.updateMessage('Sorry, I am having trouble responding right now.', true);
          this.isLoading.set(false);
          return throwError(() => new Error('Chat service error'));
        }
      });
  }

  private updateMessage(text: string, isBot = false) {
    this.messages.update(messages => [...messages, { text, isBot }]);
    this.scrollToBottom();
  }

  private trimUserMessage() {
    this.userInput = this.userInput.trim();
  }

  private simulateBotResponse() {
    setTimeout(() => {
      this.updateMessage('This is a simulated bot response.', true);
      this.isLoading.set(false);
      this.userInput = '';
    }, 2000);
  }

  private scrollToBottom() {
    try {
      this.chatHistory.nativeElement.scrollTop = this.chatHistory.nativeElement.scrollHeight;
    } catch (err) { }
  }

}
