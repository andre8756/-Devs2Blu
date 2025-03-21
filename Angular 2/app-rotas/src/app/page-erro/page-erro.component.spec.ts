import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageErroComponent } from './page-erro.component';

describe('PageErroComponent', () => {
  let component: PageErroComponent;
  let fixture: ComponentFixture<PageErroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PageErroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageErroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
