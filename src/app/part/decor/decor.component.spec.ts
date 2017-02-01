/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DecorComponent } from './decor.component';

describe('DecorComponent', () => {
  let component: DecorComponent;
  let fixture: ComponentFixture<DecorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DecorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DecorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
