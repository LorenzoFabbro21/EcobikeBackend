import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.scss']
})
export class ErrorDialogComponent {
@Output()
showModalChange = new EventEmitter<boolean>();

@Input()
  showModal = false;

@Input()
  headerText?: string;

@Input()
  showheader?: boolean;

@Input()
  labelButton = "";

@Input()
  closable = true;

@Input()
  label?: string;

onHide (): void {
  this.showModal = false;
  this.showModalChange.emit(false);
}
}
